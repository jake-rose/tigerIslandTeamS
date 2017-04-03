class Hex(int[] coord){
    int[] coord;
    int tile;
    int level;
    public void setCoord(int x, int y, int z){
        this.coord[0] = x;
        this.coord[1] = y;
        this.coord[2] = z;
    }
<<<<<<< HEAD
    public void setCoord(int[] coord){
        this.coord = coord;
    }
=======
>>>>>>> 1949656ca190d06c4aff5e8281c6d26d753243bb
    public int[] getCoord(){
        return this.coord;
    }
    public void setTile(int tile);
        this.tile = tile;
    }
    public int getTile(){
        return this.tile;
    }
    public void setLevel(int level){
        this.level = level;
    }
<<<<<<< HEAD
    //Constructor for temp hexes
=======
>>>>>>> 1949656ca190d06c4aff5e8281c6d26d753243bb
    public Hex(int x, int y, int z){
        this.coord[0] = x;
        this.coord[1] = y;
        this.coord[2] = z;
    }
<<<<<<< HEAD
    //Constructor for full hexes
    public Hex(int x, int y, int z, int tile){
        this.coord[0] = x;
        this.coord[1] = y;
        this.coord[2] = z;
        this.level = 1;
        this.tile=tile;
    }
=======
>>>>>>> 1949656ca190d06c4aff5e8281c6d26d753243bb

    @Override
    public boolean equals(Object obj){//Override equals method for sake of contains() method
        if(!obj instanceof Hex) 
            return false;
        Hex other = (Hex)obj;
<<<<<<< HEAD
        //Only examine hexes using the coordinates
=======
>>>>>>> 1949656ca190d06c4aff5e8281c6d26d753243bb
        return coord[0]=other.coord[0] && coord[1]=other.coord[1] && coord[2]=other.coord[2];
    }
}
