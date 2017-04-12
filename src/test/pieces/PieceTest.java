package pieces;

import static org.junit.Assert.*;
import pieces.*;
import org.junit.Test;

/**
 * Created by TK on 2017/4/8.
 */
public class PieceTest {
    private Piece pieces;
    @Test
    private void getType(int type){
        assertEquals(type, pieces.getType());
    }


}