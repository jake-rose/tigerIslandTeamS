class Hex(int[] coord){
    int[] coord;
    int tile;
    int level;
    public void setCoord(int x, int y, int z){
        this.coord[0] = x;
        this.coord[1] = y;
        this.coord[2] = z;
    }
    public void setCoord(int[] coord){
        this.coord = coord;
    }
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
    //Constructor for temp hexes
    public Hex(int x, int y, int z){
        this.coord[0] = x;
        this.coord[1] = y;
        this.coord[2] = z;
    }
    //Constructor for full hexes
    public Hex(int x, int y, int z, int tile){
        this.coord[0] = x;
        this.coord[1] = y;
        this.coord[2] = z;
        this.level = 1;
        this.tile=tile;
    }

    @Override
    public boolean equals(Object obj){//Override equals method for sake of contains() method
        if(!obj instanceof Hex) 
            return false;
        Hex other = (Hex)obj;
        //Only examine hexes using the coordinates
        return coord[0]=other.coord[0] && coord[1]=other.coord[1] && coord[2]=other.coord[2];
    }
}
