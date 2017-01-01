class Ship{
    private Shiptype type;
    private int length;
    private int marker;
    private int hits;
    private boolean placed;
    private boolean sunk;

    public Ship(Shiptype ship){
        this.type = ship;
        this.placed = false;
        this.sunk = false;
        this.hits = 0;
        shipDetails();
    }

    boolean setPlaced(boolean placed){this.placed = placed; return placed;}
    Shiptype getType(){return this.type;}
    int getMarker(){return this.marker;}
    int getLength(){return this.length;}
    int getHits(){return this.hits;}
    boolean isPlaced(){return this.placed;}
    boolean isSunk(){return this.sunk;}
    void hit(){this.hits++; this.sunk = (getHits() == getLength() ? true: false);}

    void shipDetails(){
        switch(type){
        case Carrier:
            this.length = 5;
            this.marker = 5;
            break;
        case Battleship:
            this.length = 4;
            this.marker = 4;
            break;
        case Cruiser:
            this.length = 3;
            this.marker = 3;
            break;
        case Submarine:
            this.length = 3;
            this.marker = 2;
            break;
        case Destroyer:
            this.length = 2;
            this.marker = 1;
            break;
        default:
            System.out.println("Ummm something went wrong");
            break;
        }
    }
}
