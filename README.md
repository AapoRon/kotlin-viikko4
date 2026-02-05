# Viikko 1–4: Tehtävälista (Jetpack Compose)

## Navigointi Jetpack Composessa
Navigointi Jetpack Composessa tarkoittaa sitä, että sovellus vaihtaa näkymiä (screen) ilman useita Activityja. Yleisin malli on **single-activity**: yksi `MainActivity`, jonka sisällä eri ruudut näytetään Compose-navigoinnin avulla.

### NavController
**NavController** on “navigaation ohjain”. Sitä käytetään siirtymiseen reittien (routes) välillä, esim.:
- `navController.navigate(ROUTE_CALENDAR)`
- `navController.popBackStack()`

### NavHost
**NavHost** määrittelee navigaation “karttanäkymän”: mikä composable näytetään milläkin reitillä. NavHostissa määritellään:
- `startDestination` (aloitusruutu)
- `composable(ROUTE_HOME) { ... }` jne.

## Sovelluksen navigaatiorakenne (Home ↔ Calendar)
Sovelluksessa on kaksi pääruutua:
- **HomeScreen** (`ROUTE_HOME`): tehtävälista
- **CalendarScreen** (`ROUTE_CALENDAR`): kalenterimainen näkymä tehtäville

Navigoinnin toteutus:
- luodaan `navController = rememberNavController()`
- määritellään `NavHost(navController, startDestination = ROUTE_HOME)`
- reiteille lisätään `composable(ROUTE_HOME)` ja `composable(ROUTE_CALENDAR)`

Käyttäjä siirtyy Home → Calendar painamalla “Kalenteri”, ja Calendar → Home painamalla “Lista/Takaisin” (navigate tai popBackStack).

---

## Arkkitehtuuri: MVVM + navigointi
Sovellus noudattaa MVVM-mallia:

- **Model**: `Task` (domain-kerros)
- **ViewModel**: `TaskViewModel` (sisältää sovelluksen tilan ja logiikan)
- **View**: `HomeScreen` ja `CalendarScreen` (Compose UI)

### Yksi ViewModel kahdelle screenille
Jotta sama tila näkyy molemmissa ruuduissa, `TaskViewModel` luodaan **NavHostin tasolla** ja annetaan parametrina molemmille screeneille. Näin ViewModelia ei luoda uudestaan navigoinnin yhteydessä.

### Tilan jakaminen ruutujen välillä
ViewModel pitää tehtävälistan tilan (StateFlow / state). Molemmat ruudut “tilaavat” saman tilan:
- `val tasks by taskViewModel.tasks.collectAsState()`

Kun tehtävää lisätään, poistetaan tai muokataan HomeScreenillä, muutos päivittyy automaattisesti myös CalendarScreeniin, koska molemmat käyttävät samaa ViewModelin tilaa.

---

## CalendarScreenin toteutus
CalendarScreen näyttää tehtävät “kalenterimaisesti” ryhmittelemällä ne `dueDate`-kentän mukaan:
- tehtävät järjestetään päivämäärän mukaan
- tehtävät ryhmitellään `groupBy { it.dueDate }`
- jokainen päivä näytetään otsikkona (esim. `2026-01-30`)
- otsikon alla listataan kyseisen päivän tehtävät

Näin käyttäjä näkee selkeästi, mihin päivään kukin tehtävä liittyy.

---

## AlertDialog: addTask ja editTask
Sovelluksessa tehtävien lisääminen ja muokkaus tapahtuu **AlertDialog**-ikkunoissa (ei omilla navigaatioreiteillä).

### addTask (Lisää tehtävä)
HomeScreenissä on “+ / Add” -toiminto, joka avaa AlertDialogin:
- käyttäjä syöttää vähintään `title`
- “Tallenna” kutsuu `viewModel.addTask(title)`
- “Peruuta” sulkee dialogin ilman muutoksia

### editTask (Muokkaa / Poista)
Kun käyttäjä valitsee tehtävän (listassa tai kalenterissa), avautuu AlertDialog:
- kentät ovat esitäytetty valitun taskin tiedoilla
- “Tallenna” kutsuu `viewModel.updateTask(updatedTask)`
- “Poista” kutsuu `viewModel.removeTask(id)`
- “Peruuta” sulkee dialogin

Tämä pitää navigoinnin yksinkertaisena: lisääminen ja muokkaus ovat dialogeja, eivät omia reittejä.
