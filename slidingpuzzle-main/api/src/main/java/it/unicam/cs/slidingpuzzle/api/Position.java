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

import java.util.*;

/**
 * The instances of this class are used to identify positions in the schema.
 */
public class Position {

    private final int row;

    private final int column;

    private final int size;

    public Position(int row, int column, int size) {
        this.row = row;
        this.column = column;
        this.size = size;
    }

    public Position(int size) {
        this(size-1, size-1, size);
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getSize() {
        return size;
    }

    /**
    Il metodo movingCell(SlidingDirection dir) nella classe Position è responsabile per
    determinare la nuova posizione che verrebbe raggiunta muovendo una cella nella direzione
     specificata dal parametro dir. Esamina la direzione richiesta e, se il movimento è
     possibile, restituisce una nuova posizione corrispondente.
     Vediamo i dettagli di ciascuna parte del metodo:

    Parametro
    dir: un valore dell'enumerazione SlidingDirection, che può essere UP, DOWN, LEFT, o RIGHT.
    Rappresenta la direzione in cui si desidera muovere la cella.

    CORPO DEL METODO
    1.Se la direzione è DOWN e la riga è maggiore di 0:
    Crea e restituisce una nuova posizione con la riga diminuita di 1,
     mantenendo invariata la colonna e la dimensione. In altre parole, muove
     la cella di una posizione verso l'alto.
    La condizione row > 0 assicura che il movimento sia valido
     (non siamo già nella riga superiore).

    2.Se la direzione è UP e la riga è minore di size-1:
     Crea e restituisce una nuova posizione con la riga aumentata di 1,
     mantenendo invariata la colonna e la dimensione.
     In altre parole, muove la cella di una posizione verso il basso.
    La condizione row < size-1 assicura che il movimento sia valido
     (non siamo già nella riga inferiore).

    3.Se la direzione è LEFT e la colonna è minore di size-1:
     Crea e restituisce una nuova posizione con la colonna aumentata di 1,
     mantenendo invariata la riga e la dimensione. In altre parole,
     muove la cella di una posizione verso destra.
    La condizione column < size-1 assicura che il movimento sia valido
     (non siamo già nella colonna più a destra).

    4.Se la direzione è RIGHT e la colonna è maggiore di 0:
     Crea e restituisce una nuova posizione con la colonna diminuita di 1,
     mantenendo invariata la riga e la dimensione. In altre parole,
     muove la cella di una posizione verso sinistra.
    La condizione column > 0 assicura che il movimento
     sia valido (non siamo già nella colonna più a sinistra).

    5.Se nessuna delle condizioni precedenti è soddisfatta:
     Restituisce null, indicando che il movimento richiesto non è valido.

    RIASSUNTO
    Il metodo movingCell prende una direzione come input e restituisce
     una nuova posizione che rappresenta la cella spostata in quella direzione,
     se il movimento è valido. Se il movimento non è valido, restituisce null.
     Viene utilizzato per gestire i movimenti all'interno del gioco del 15,
     garantendo che i movimenti siano effettuati entro i confini della griglia.
     */
    public Position movingCell(SlidingDirection dir) {

        if ((dir == SlidingDirection.DOWN)
                &&(row>0))
            return new Position(row-1,column,size);

        if ((dir == SlidingDirection.UP)
                &&(row<size-1))
            return new Position(row+1,column, size);

        if ((dir == SlidingDirection.LEFT)
                &&(column<size-1))
            return new Position(row, column+1, size);

        if ((dir == SlidingDirection.RIGHT)
                &&(column>0))
            return new Position(row, column-1, size);

        return null;
    }


    /**
     Il metodo enabledMoves nella classe Position ha il compito di determinare quali mosse
     sono possibili dalla posizione corrente all'interno di una griglia quadrata.
     Restituisce un array delle direzioni valide in cui la cella corrente può essere mossa.
     Ecco una spiegazione dettagliata di come funziona:

     Creazione della Lista delle Direzioni Valide
      Inizializzazione di una Lista Collegata (LinkedList):
      La LinkedList<SlidingDirection> chiamata nextPositions
     viene inizializzata per memorizzare temporaneamente le direzioni valide.

      VERIFICA DELLE DIREZIONI VALIDE
      1.Controllo per la Direzione UP:
      Se la riga corrente è maggiore di 0 (row > 0), significa che la cella
     non si trova già nella riga superiore della griglia e
     quindi può essere mossa verso l'alto. La direzione SlidingDirection.UP
     viene quindi aggiunta alla lista nextPositions.

      2.Controllo per la Direzione DOWN:
     Se la riga corrente è minore della dimensione della griglia meno uno
     (row < size - 1), significa che la cella non si trova già nella riga inferiore
     della griglia e quindi può essere mossa verso il basso. La direzione SlidingDirection.DOWN
     viene quindi aggiunta alla lista nextPositions.

     3.Controllo per la Direzione LEFT:
     Se la colonna corrente è maggiore di 0 (column > 0),
     significa che la cella non si trova già nella colonna più a sinistra della
     griglia e quindi può essere mossa verso sinistra. La direzione SlidingDirection.LEFT
     viene quindi aggiunta alla lista nextPositions.

      4.Controllo per la Direzione RIGHT:
      Se la colonna corrente è minore della dimensione della griglia meno uno
     (column < size - 1), significa che la cella non si trova già nella colonna più
     a destra della griglia e quindi può essere mossa verso destra.
     La direzione SlidingDirection.RIGHT viene quindi aggiunta alla lista nextPositions.

      CONVERSIONE DELLA LISTA IN ARRAY:
      Conversione della Lista in Array:
      La lista linkata nextPositions viene convertita in un array utilizzando
     il metodo toArray e viene restituito.

      RIASSUNTO
      Il metodo enabledMoves esamina la posizione corrente all'interno della griglia e
     determina in quali direzioni è possibile muovere la cella senza uscire dai confini
     della griglia. Restituisce un array di direzioni valide che possono essere utilizzate
     per guidare le mosse nel gioco del 15. Le direzioni valide sono rappresentate
     dall'enumerazione SlidingDirection, che può contenere i valori UP, DOWN, LEFT e RIGHT.

     */
    public SlidingDirection[] enabledMoves() {

        LinkedList<SlidingDirection> nextPositions = new LinkedList<>();
        if (row > 0)
            nextPositions.add(SlidingDirection.UP);    // Cambiato da LEFT a UP
        if (row < size - 1)
            nextPositions.add(SlidingDirection.DOWN);  // Cambiato da RIGHT a DOWN
        if (column > 0)
            nextPositions.add(SlidingDirection.LEFT);  // Rimossa la condizione duplicata
        if (column < size - 1)
            nextPositions.add(SlidingDirection.RIGHT); // Cambiato da UP a RIGHT

        return nextPositions.toArray(new SlidingDirection[0]);
        /**
         La chiamata di metodo nextPositions.toArray(new SlidingDirection[0]) è una tecnica
         utilizzata per convertire una lista (in questo caso, una LinkedList) in un array.
         Vediamo cosa significa ogni parte di questa chiamata:

         NEXTPOSITION: Si tratta della variabile che rappresenta la lista
         (in questo caso, una LinkedList<SlidingDirection>) che si desidera convertire in un array.

         .TOARRAY(): Questo è un metodo disponibile nelle classi che implementano
         l'interfaccia List in Java. Viene utilizzato per convertire la lista in un array.

         (new SlidingDirection[0]): Questa è una chiamata al costruttore dell'array che crea
         un nuovo array di tipo SlidingDirection con lunghezza 0.
         Questo array vuoto viene passato come argomento al metodo toArray.

         COME FUNZIONA
         Passare un array di lunghezza 0 con il tipo desiderato come argomento al
         metodo toArray serve a indicare al metodo quale tipo di array deve essere creato.
         In questo caso, si desidera un array di tipo SlidingDirection.

         Anche se l'array passato ha lunghezza 0, il metodo toArray creerà un nuovo array
         della dimensione corretta per contenere tutti gli elementi della lista.
         Il tipo dell'array sarà lo stesso del tipo dell'array passato come argomento
         (in questo caso, SlidingDirection).

         ESEMPIO
         Se nextPositions contiene le direzioni [UP, DOWN, LEFT], la chiamata
         a nextPositions.toArray(new SlidingDirection[0]) restituirà un array di
         SlidingDirection che contiene gli stessi elementi: [UP, DOWN, LEFT].

         RIASSUNTO
         La chiamata di metodo nextPositions.toArray(new SlidingDirection[0])
         converte la lista nextPositions in un array di tipo SlidingDirection,
         preservando l'ordine degli elementi. È un modo comune e conciso per convertire
         una lista in un array in Java.
         */
    }

    /**
     * Returns the Manhattan distance obtained when considering the
     * value n placed in this location.
     *
     * @param n an integer value
     * @return the Manhattan distance obtained when considering the
     * value n placed in this location.
     */
    public int getDisorderDegree(int n) {
        // |rg - rc|+|cg - cc|
        return Math.abs(this.row-(n-1)/this.size) + Math.abs(this.column-(n-1)%this.size);
        /**
         * Facciamo un esempio utilizzando il metodo getDisorderDegree dalla classe Position.
         * Supponiamo di avere una griglia di dimensione 3x3 (quindi size = 3) e vogliamo
         * calcolare il grado di disordine per il valore
         * n=5 che si trova nella posizione corrente (row = 1, column = 0).

         * PASSAGGI:
         * Calcolo della Posizione Corretta di n:
         * Riga Corretta (rg): (n−1)/size=(5−1)/3=4/3=1

         * Colonna Corretta (cg): (n−1)%size=(5−1)%3=4%3=1

         * Calcolo della Differenza tra la Posizione Corretta e la Posizione Corrente:

         * Differenza di Riga: Math.abs(1−1)=Math.abs(0)=0
         * Differenza di Colonna: Math.abs(0−1)=Math.abs(−1)=1

         * Somma delle Differenze: 0+1=1

         * Ritorno del Risultato:
         * Il metodo restituisce 1, che rappresenta il grado di disordine per il valore
         * n=5 rispetto alla sua posizione corretta nel puzzle risolto.

         * Interpretazione:
         * Il risultato indica che il valore
         * n=5 è distante 1 posizione dalla sua posizione corretta in un puzzle risolto.
         * In altre parole, per risolvere il puzzle, questo valore dovrà essere spostato di una
         * posizione nella griglia.
         */
    }
}
