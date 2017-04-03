class Hex(int[] coord){
    int[] coord;
    int tile;
    int level;
    public void setCoord(int x, int y, int z){
        this.coord[0] = x;
        this.coord[1] = y;
        this.coord[2] = z;
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
    public Hex(int x, int y, int z){
        this.coord[0] = x;
        this.coord[1] = y;
        this.coord[2] = z;
    }

    @Override
    public boolean equals(Object obj){//Override equals method for sake of contains() method
        if(!obj instanceof Hex) 
            return false;
        Hex other = (Hex)obj;
        return coord[0]=other.coord[0] && coord[1]=other.coord[1] && coord[2]=other.coord[2];
    }
}
