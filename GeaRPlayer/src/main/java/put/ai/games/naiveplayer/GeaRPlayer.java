/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package put.ai.games.naiveplayer;

import java.util.List;
import java.util.Random;
import put.ai.games.game.Board;
import put.ai.games.game.Move;
import put.ai.games.game.Player;
import put.ai.games.game.TypicalBoard;

import static java.lang.Math.*;

public class GeaRPlayer extends Player {

    private String kto="MAX";
    List<Move> moves;
    int glebokosc;
    private Random random=new Random(0xdeadbeef);

    @Override
    public String getName() {
        return "Robert Walicki 127247 Grzegorz Wilczynski 127266";
    }
    private int ocena(Move move,Board b)
    {

        b.doMove(move);
        int ocena;
        ocena=((TypicalBoard)b).countStones(getOpponent(getColor()))-((TypicalBoard)b).countStones(getColor());

        b.undoMove(move);
        return ocena;
    }

    @Override
    public Move nextMove(Board b) {
        moves = b.getMovesFor(getColor());

        switch (getColor())
        {
            case PLAYER1:
            {
                kto="MAX";
            }
            case PLAYER2:
            {
                kto="MIN";
            }
        }
        glebokosc= (int) floor(log10(moves.size())/log10(2));
        return alfabeta(moves.get(0),0,Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY,kto,b);
    }
    private Move alfabeta(Move state, int depth,double alpha,double beta, String type,Board b)
    {
        int parentIndex=moves.indexOf(state);

        if(depth==glebokosc || (int) (parentIndex*2)>=moves.size() || getTime()<1000)
        {
            return state; }
        if(type=="MIN")
        {
            Move child=state;
            double better=beta;
            for(int i=0;i<2&& (int) (parentIndex*2+i)<moves.size();i++)
            {



                beta=min(beta,ocena(alfabeta(moves.get(parentIndex*2+i),depth+1,alpha,beta,"MAX",b),b));
                if(beta!=better)
                {
                    child=moves.get((int) parentIndex*2+i);
                    better=beta;
                }
                if(alpha>=beta)
                {
                    break;
                }
            }
            return child;

        }
        else if(type=="MAX")
        {
            Move child=state;
            double alfer=alpha;
            for(int i=0;i<2 && (int) parentIndex*2+i<moves.size();i++)
            {


                alpha=max(alpha,ocena(alfabeta(moves.get(parentIndex*2+i),depth+1,alpha,beta,"MIN",b),b));
                if(alpha!=alfer)
                {
                    child=moves.get(parentIndex*2+i);
                    alfer=alpha;
                }
                if(alpha>=beta)
                {
                    break;
                }
            }
            return child;
        }
        return state;
    }
    public static void main(String args[]) {


    }
}
