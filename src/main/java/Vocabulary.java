public class Vocabulary {
    private String word;
    private String image;
    private String information;

    @Override
    public String toString() {
        return "Vocabulary{" +
                "word='" + word + '\'' +
                ", image='" + image + '\'' +
                ", information='" + information + '\'' +
                ", pronounciation='" + pronounciation + '\'' +
                ", audio='" + audio + '\'' +
                '}';
    }

    private String pronounciation;
    private String audio;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getPronounciation() {
        return pronounciation;
    }

    public void setPronounciation(String pronounciation) {
        this.pronounciation = pronounciation;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public Vocabulary() {
    }

    public Vocabulary(String word, String image, String information, String pronounciation, String audio) {
        this.word = word;
        this.image = image;
        this.information = information;
        this.pronounciation = pronounciation;
        this.audio = audio;
    }
}
