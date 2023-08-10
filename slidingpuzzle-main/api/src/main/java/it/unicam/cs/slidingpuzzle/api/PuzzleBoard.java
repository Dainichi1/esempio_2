/*
 * MIT License
 *
 * Copyright (c)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */
package it.unicam.cs.slidingpuzzle.api;

import java.util.Random;

/**
 * This class is used to represent the schema of a puzzle.
 */
public class PuzzleBoard {

    /**
     * Default size of a schema.
     */
    public static final int DEFAULT_SIZE = 4;

    /**
     * An array used to store the state of this schema.
     */
    private final int[][] cells;

    /**
     * The size of this schema.
     */
    private final int size;

    private Position freeCell;
    private int shufflingDegree;

    /**
     * Creates a new puzzle with the default size.
     */
    public PuzzleBoard() {
        this(DEFAULT_SIZE);
    }

    /**
     * Creates a new puzzle of the given size.
     *
     * @param size the size of the created schema.
     */
    public PuzzleBoard(int size) {
        this.size = size;
        this.cells = new int[size][size];
        reset();
    }

    /**
     * Resets the state of this schema.
     */
    private void reset() {
        shufflingDegree = 0;
        // shufflingDegree: Viene impostato a 0, indicando che il puzzle è in uno
        // stato ordinato (risolto).

        freeCell = new Position(size);
        //freeCell: Viene impostata alla posizione in basso a destra della griglia,
        // utilizzando il costruttore della classe Position con un solo parametro,
        // che inizializza la posizione alla cella in basso a destra.
        // In un puzzle del 15 risolto, l'ultima cella (in basso a destra) è la cella vuota.

        int counter = 1;
        for(int i=0; i < size; i++) {
            for(int j=0; j < size; j++) {
                this.cells[i][j] = (counter++)%(size*size);
                // Consideriamo un esempio in cui la dimensione della griglia del puzzle (size)
                // è 3. Ecco come funzionerà il frammento di codice nel metodo reset:
                //
                // 1.Inizializza counter con il valore 1.
                // 2.Entra nel primo ciclo for, che itera sulle righe da 0 a 2 (poiché size=3).
                // 3.Entra nel secondo ciclo for, che itera sulle colonne da 0 a 2.
                // 4.Assegna i valori alle celle come segue:
                //    cells[0][0]=(1++)%9=1 (e poi incrementa counter a 2)
                //    cells[0][1]=(2++)%9=2 (e poi incrementa counter a 3)
                //    cells[0][2]=(3++)%9=3 (e poi incrementa counter a 4)
                //    cells[1][0]=(4++)%9=4 (e poi incrementa counter a 5)
                //    cells[1][1]=(5++)%9=5 (e poi incrementa counter a 6)
                //    cells[1][2]=(6++)%9=6 (e poi incrementa counter a 7)
                //    cells[2][0]=(7++)%9=7 (e poi incrementa counter a 8)
                //    cells[2][1]=(8++)%9=8 (e poi incrementa counter a 9)
                //    cells[2][2]=(9++)%9=0 (e poi incrementa counter a 10, ma
                //    non viene più utilizzato)
                //
                // La griglia risultante sarà:
                //       1 2 3
                //       4 5 6
                //       7 8 0
                //Dove la cella con il valore 0 rappresenta la cella vuota del puzzle.
                // Questo è lo stato iniziale del puzzle, con i numeri disposti in ordine e
                // la cella vuota nell'angolo in basso a destra.
            }
        }
    }

    public boolean move(SlidingDirection dir) {
        // Facciamo un esempio pratico per illustrare come funziona il metodo move nella classe
        // PuzzleBoard. Consideriamo una griglia 3x3 e supponiamo che vogliamo muovere la
        // cella vuota nella direzione "UP". La griglia iniziale potrebbe essere:
        // 1 2 3
        // 4 0 5
        // 6 7 8
        //La cella vuota è rappresentata dal valore 0 e si trova nella posizione
        // (row = 1, column = 1).
        Position movingCell = freeCell.movingCell(dir);
        if (movingCell == null) {
            return false;
            // Determina la Nuova Posizione della Cella Vuota:
            // Chiamando freeCell.movingCell(SlidingDirection.UP),
            // otteniamo la posizione (row = 0, column = 1).
            // Controlla se la Mossa è Valida:
            // La mossa è valida, quindi continuiamo.
        }

        int n = get(movingCell);
        // Recupera il valore della cella che verrà spostata nella posizione della cella vuota.
        // Nel nostro esempio: La posizione movingCell è (row = 0, column = 1), quindi n = 2.

        set(freeCell, n);
        // Assegna il valore n alla posizione attuale della cella vuota,
        // spostando effettivamente il valore n dalla sua posizione corrente alla posizione
        // della cella vuota.
        // Nel nostro esempio: La posizione della cella vuota è
        // (row = 1, column = 1), quindi il valore 2 viene spostato lì.
        //La griglia diventa:
        //  1 2 3
        //  4 2 5
        //  6 7 8

        set(movingCell, 0);
        // Assegna il valore 0 (che rappresenta la cella vuota) alla posizione movingCell,
        // rendendo quella posizione la nuova cella vuota.
        // Nel nostro esempio: La posizione movingCell è (row = 0, column = 1),
        // quindi quella posizione viene impostata come cella vuota.
        // La griglia diventa:
        // 1 0 3
        // 4 2 5
        // 6 7 8

        shufflingDegree = shufflingDegree-movingCell.getDisorderDegree(n)+freeCell.getDisorderDegree(n);
        freeCell = movingCell;
        return true;
    }

    private int get(Position p) {
        return get(p.getRow(), p.getColumn());
        // Il metodo get nella classe PuzzleBoard è un metodo privato che serve a
        // recuperare il valore contenuto in una determinata posizione della griglia del puzzle.
        // Supponiamo di avere una griglia 3x3 come segue:
        //
        // 1 2 3
        // 4 5 6
        // 7 8 0
        //
        //Se chiamiamo il metodo get con una posizione che rappresenta la riga 1 e la colonna 2
        // (utilizzando una numerazione basata su zero),
        // il metodo restituirà il valore 6, che è l'elemento contenuto in quella posizione
        // della griglia.
        //
    }

    private void set(Position p, int v) {
        this.cells[p.getRow()][p.getColumn()] = v;
        // Il metodo set nella classe PuzzleBoard è un metodo privato utilizzato per
        // assegnare un valore specifico a una posizione specificata nella griglia del puzzle.
        // Supponiamo di avere la seguente griglia 3x3:
        //
        // 1 2 3
        // 4 5 6
        // 7 8 0

        //Vogliamo impostare il valore 9 nella posizione con riga 2 e colonna 1
        // (utilizzando una numerazione basata su zero).
        //
        // Creiamo un oggetto Position con le coordinate row = 2, column = 1.
        //
        // Chiamiamo il metodo set con l'oggetto Position e il valore v = 9.
        //
        // Il metodo estrae la riga e la colonna dall'oggetto Position, che sono 2 e 1
        // rispettivamente.
        //
        // Accede all'elemento nella riga 2 e colonna 1 della griglia e assegna il valore 9.
        //
        // La griglia risultante sarà:
        //
        // 1 2 3
        // 4 5 6
        // 7 9 0

    }


    public int get(int x, int y) {
        return this.cells[x][y];
        // Il metodo get(int x, int y) nella classe PuzzleBoard è un metodo pubblico
        // che restituisce il valore contenuto in una posizione specificata della griglia
        // del puzzle.
        //
        // Supponiamo di avere la seguente griglia 3x3:
        //
        // 1 2 3
        // 4 5 6
        // 7 8 0
        //
        //Vogliamo recuperare il valore dalla posizione con riga 1 e colonna 2
        // (utilizzando una numerazione basata su zero).
        //
        //Chiamiamo il metodo get con x = 1 e y = 2.

        // Il metodo accede all'elemento nella riga 1 e colonna 2 della griglia,
        // che contiene il valore 6.
        // Restituisce il valore 6.
    }

    public void shuffle(int movements) {
        shuffle(new Random(), movements);
        // Il metodo shuffle(int movements) nella classe PuzzleBoard è utilizzato per mescolare
        // il puzzle, cioè per spostare le celle in posizioni casuali, al fine di
        // creare una disposizione iniziale disordinata.
        // Ecco una spiegazione dettagliata del suo funzionamento, passo per passo:
        //
        //Parametro:
        //int movements: Il numero di mosse casuali che si desidera effettuare
        // per mescolare il puzzle.

        //Passaggi:
        //
        //Creazione di un Oggetto Random:
        // Crea un nuovo oggetto Random che sarà utilizzato per generare numeri casuali.
        // Questi numeri casuali determineranno le direzioni in cui le celle saranno spostate
        // durante la fase di mescolamento.
        //
        //Chiamata al Metodo shuffle Sovraccaricato:
        //Chiama un altro metodo shuffle (probabilmente sovraccaricato) che accetta
        // sia l'oggetto Random sia il numero di movimenti come parametri.
        //Questo metodo sovraccaricato eseguirà effettivamente le mosse casuali,
        // utilizzando l'oggetto Random per scegliere le direzioni in cui spostare le celle.
        //
        //Mescolamento del Puzzle:
        //Il metodo sovraccaricato shuffle eseguirà il numero specificato di mosse casuali,
        // spostando le celle in direzioni casuali. Questo mescolerà il puzzle,
        // creando una disposizione iniziale disordinata.
        //
        //Fine:
        //Il metodo non restituisce nulla (void), quindi termina una volta che
        // il mescolamento è completo.

        //Esempio Pratico:
        //Supponiamo di avere la seguente griglia 3x3 ordinata:
        //
        // 1 2 3
        // 4 5 6
        // 7 8 0
        //Chiamiamo il metodo shuffle con movements = 10.
        //
        //Il metodo crea un oggetto Random e chiama il metodo shuffle sovraccaricato con
        // questo oggetto e il numero di movimenti.
        //Il metodo sovraccaricato esegue 10 mosse casuali, spostando le celle in
        // direzioni casuali.
        //La griglia del puzzle viene mescolata in una disposizione disordinata.
    }

    public void shuffle(Random random, int movements) {
        for(int i=0; i<movements; i++) {
            SlidingDirection[] enabledMovement = enabledMoves();
            // Chiama il metodo enabledMoves() per ottenere un array di direzioni in cui
            // la cella vuota può essere spostata. Queste sono le mosse valide che possono
            // essere effettuate in quel momento.
            // Ad esempio, se la cella vuota è circondata da altre celle su tre lati,
            // ci sarà solo una direzione in cui può essere spostata.

            if (enabledMovement.length>0) {
                move(enabledMovement[random.nextInt(enabledMovement.length)]);
                // enabledMovement:
                // Un array che contiene le direzioni valide in cui
                // la cella vuota può essere spostata in quel momento.
                // Ad esempio, potrebbe contenere [UP, LEFT] se la cella
                // vuota può essere spostata solo in su o a sinistra.
                //
                // enabledMovement.length:
                // La lunghezza dell'array, che rappresenta il numero di mosse valide
                // possibili. Nel nostro esempio, la lunghezza sarebbe 2.
                //
                // random.nextInt(enabledMovement.length):
                // Genera un numero casuale tra 0
                // (incluso) e la lunghezza dell'array (escluso). Nel nostro esempio,
                // potrebbe generare 0 o 1.
                //
                // enabledMovement[random.nextInt(enabledMovement.length)]:
                // Utilizza il numero casuale come indice per selezionare una direzione
                // dall'array. Nel nostro esempio, potrebbe selezionare UP o LEFT.
                //
                // move(...):
                // Chiama il metodo move con la direzione selezionata,
                // effettuando effettivamente la mossa e spostando la cella vuota
                // in quella direzione.
                //
                // Supponiamo di avere la seguente griglia 3x3:
                //
                // 1 2 3
                // 4 5 6
                // 7 8 0
                //
                //La cella vuota può essere spostata solo in su o a sinistra,
                // quindi enabledMovement = [UP, LEFT].
                //
                //Supponiamo che random.nextInt(2) generi 1.
                //Quindi, enabledMovement[1] seleziona LEFT dall'array.
                //Infine, move(LEFT) viene chiamato, spostando la cella vuota a sinistra.

                //Griglia dopo la mossa:
                //
                // 1 2 3
                // 4 5 6
                // 7 0 8

            }
        }
    }

    public SlidingDirection[] enabledMoves() {
        return freeCell.enabledMoves();
        // Il metodo enabledMoves() nella classe PuzzleBoard è un metodo pubblico
        // che restituisce un array delle direzioni valide in cui la cella vuota
        // del puzzle può essere spostata. Ecco una spiegazione dettagliata del suo
        // funzionamento, passo per passo:
        //
        //Passaggi:
        //Accesso alla Cella Vuota:
        //La classe PuzzleBoard mantiene una variabile membro freeCell che rappresenta
        // la posizione della cella vuota nel puzzle. Questa è una posizione speciale che
        // può essere spostata nelle direzioni valide secondo le regole del puzzle.

        // Chiamata al Metodo enabledMoves() della Cella Vuota:
        //Chiama il metodo enabledMoves() sull'oggetto freeCell. Questo metodo è definito
        // nella classe Position e restituisce un array delle direzioni valide in cui la
        // cella vuota può essere spostata.
        //Le direzioni valide dipendono dalla posizione attuale della cella vuota nella griglia.
        // Ad esempio, se la cella vuota è in un angolo, potrebbe avere solo due direzioni valide.

        //Restituzione delle Direzioni Valide:
        //Restituisce l'array delle direzioni valide ottenuto dalla chiamata al
        // metodo sulla cella vuota. Queste direzioni rappresentano le mosse che possono
        // essere effettuate in quel momento nel gioco.

        //Esempio Pratico:
        //Supponiamo di avere la seguente griglia 3x3:
        //
        // 1 2 3
        // 4 5 6
        // 7 8 0
        //
        //La cella vuota è nella posizione in basso a destra.
        //
        //Chiamiamo il metodo enabledMoves().
        //La posizione della cella vuota permette solo di spostarla in su (UP) o a sinistra (LEFT).
        //Il metodo restituisce un array con queste due direzioni: [UP, LEFT].
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty(int i, int j) {
        return cells[i][j]==0;
    }

    public boolean solved() {
        return this.shufflingDegree==0;
    }
}
