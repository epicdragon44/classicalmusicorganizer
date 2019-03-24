public class Track {
    private String name;
    private int countTrack;
    public Track(String name) {
        this.name = name;
        countTrack = 0;
    }
    public void incrementTrackCount() {
        countTrack++;
    }
    public int getCountTrack() {
        return countTrack;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        return this.name.equals(((Track)(obj)).name);
    }
}
