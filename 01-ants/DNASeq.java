
package ants;

/** Support class for Ants.java
 *  Constructor takes a DNA sequence.
 *  Methods allow us to access state, compass position changes for ant and the state
 *  changes for the square object we are currently on.
 */
public class DNASeq {
    
    protected String state;
    protected String compassPosChanges;
    protected String stateChanges;

    /** Constructor method.
     *  @param state state for sequence
     *  @param compassPosChanges 4 characters representing where the ant will travel next
     *  if on current state.
     *  @param stateChanges 4 characters that are the state changes of the square object
     *  after it hits the coinciding compass direction.
     */
    public DNASeq(String state, String compassPosChanges, String stateChanges){
        this.state = state;
        this.compassPosChanges = compassPosChanges;
        this.stateChanges = stateChanges;
    }

    /** returns state of sequence.
     *  @return state
     */
    public char getState(){
        return state.charAt(0);
    }

    /** gets the new compass direction at a set index corresponding to previous direction
     *  N, E, S or W.
     *  @return new compass direction
     */
    public char getCompassPos(int i){
        return compassPosChanges.charAt(i);
    }
    /** get the new state corresponding to the new compass direction.
     * @return new state.
     */
    public char getNewState(int i){
        return stateChanges.charAt(i);
    }

    /** prints out the contents of the sequence.
     */
    public void printSeq(){
        System.out.println(state+" "+compassPosChanges+" "+stateChanges);
    }
}
