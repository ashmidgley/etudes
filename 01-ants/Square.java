
package ants;

import java.awt.Point;

/** Supports main class Ants.java.
 *  Builds a square object that has a state value and a coordinate position.
 */
public class Square {
    
    protected char state;
    protected Point coord;

    /** Constructor method.
     * @param coord square coordinate.
     * @param def default state for square.
     */
    public Square(Point coord, char def){
        state = def;
        this.coord = coord;
    }

    /** changes the state of the square.
     *  @param s state we are changing to
     */
    public void flipState(char s){
        this.state = s;

        /**if(state == 'w'){
            state = 'b';
        }else{
            state = 'w';
            }*/
    }

    /** sets the state of square.
     *  @param s state we are setting.
     */
    public void setState(char s){
        state = s;
    }

    /** returns the state of the square.
     *  @return state state of square.
     */
    public char getState(){
        return state;
    }

    /** gets the coordinate of the square in the map.
     * @return coord coordinate of square.
     */
    public Point getCoord(){
        return coord;
    }
}
