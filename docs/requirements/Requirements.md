# **Specifica dei Requisiti Software (SRS) \- Splitwise Clone**

## **1\. Introduzione**

Il sistema è un'applicazione per la gestione e la divisione delle spese condivise tra gruppi di persone. L'obiettivo principale è fornire un calcolo accurato dei debiti e un algoritmo di semplificazione delle transazioni.

## **2\. Requisiti Funzionali (FR)**

I requisiti funzionali descrivono le funzionalità specifiche che il sistema deve fornire.

| ID        | Categoria | Descrizione | Priorità       | Note di Design                             |
|:----------| :---- | :---- |:---------------|:-------------------------------------------|
| **FR-01** | **Utenti** | Il sistema deve permettere la creazione di un utente con nome, email e ID univoco. | **MVP (Core)** | Entità User.                               |
| **FR-02** | **Gruppi** | Il sistema deve permettere la creazione di gruppi di spesa con nome e descrizione. | **MVP (Core)** | Entità Group.                              |
| **FR-03** | **Membri** | Il sistema deve permettere l'aggiunta e la rimozione di utenti da un gruppo esistente. | **MVP (Core)** | Relazione Aggregazione Group o-- User.     |
| **FR-04** | **Spesa** | Il sistema deve permettere l'inserimento di una spesa specificando importo, valuta, pagante e lista dei partecipanti. | **MVP (Core)** | Entità Expense + Entità Money.             |
| **FR-05** | **Strategia di Divisione** | Il sistema deve permettere la selezione dinamica della logica di ripartizione del costo tra i membri del gruppo. | **MVP (Core)** | ???                                        |
| **FR-06** | **Calcolo Bilancio** | Il sistema deve calcolare i saldi (crediti/debiti) per un utente specifico basandosi su tutte le spese registrate. | **MVP (Core)** | BalanceService.                            |
| **FR-07** | **Ottimizzazione** | Il sistema deve fornire una vista "Semplificata" dei debiti minimizzando il numero di transazioni tramite un algoritmo di Cash Flow Minimization.| **MVP (Core)** | Activity Diagram dedicato.                 |
| **FR-08** | **Persistenza** | Il sistema deve garantire l'integrità dei dati tramite un'interfaccia di persistenza che isoli la logica di business dallo storage. | **MVP (Core)** | Pattern **Repository**.                    |
| **FR-09** | **Saldare Debiti** | Il sistema deve permettere di registrare un pagamento di rimborso tra due utenti ("Settle Up"). | **MVP (Core)** | Si tratta di una spesa inversa.            |
| **FR-10** | **Notifiche** | Il sistema deve notificare i partecipanti quando viene aggiunta una nuova spesa che li coinvolge. | **Estensione** | Pattern **Observer** (NotificationSender). |
| **FR-11** | **Gestione Valute** | Il sistema deve supportare l'uso di valute diverse (es. EUR, USD). | **Estensione** | Pattern Adapter                            |
| **FR-12** | **Modifica Spesa** | Il sistema deve permettere la modifica di una spesa esistente, ricalcolando i bilanci e inviando notifiche di aggiornamento. | **Estensione** | Complesso per la consistenza dei dati.     |
| **FR-13** | **Ricerca Utenti** | Il sistema deve permettere di cercare utenti al di fuori dei gruppi di appartenenza. | **Estensione** | Richiede un Repository Utenti globale.     |

## **3\. Requisiti Non Funzionali (NFR)**

I requisiti non funzionali descrivono gli attributi di qualità del sistema (performance, sicurezza, manutenibilità).

| ID | Attributo                | Descrizione                                                                                                                                          |
| :---- |:-------------------------|:-----------------------------------------------------------------------------------------------------------------------------------------------------|
| **NFR-01** | **Precisione**           | I calcoli monetari non devono mai perdere precisione decimale (es. divisione 10/3). Deve essere usato BigDecimal.                                    |
| **NFR-02** | **OCP (Open/Closed)**    | Nuove modalità di split o di notifica devono essere integrabili tramite interfacce, senza modificare le classi core                                  |
| **NFR-03** | **Dependency Inversion** | I moduli di alto livello (Business Logic) non devono dipendere da moduli di basso livello (Database/IO), ma entrambi devono dipendere da astrazioni  |
| **NFR-04** | **Usabilità API**        | Le interfacce dei Service non devono conoscere nulla dell'interfaccia utente (CLI, GUI, Web).                                                        |
| **NFR-05** | **Modularità** | L'architettura deve seguire una separazione netta tra i componenti, garantendo che il calcolo del bilancio sia indipendente dal sistema di persistenza scelto. |

## **4\. Vincoli di Progetto**

* **Linguaggio:** Java.
* **IDE:** IntelliJ IDEA Ultimate.
* **Architettura:** Layered Architecture (Controller \-\> Service \-\> Repository).



