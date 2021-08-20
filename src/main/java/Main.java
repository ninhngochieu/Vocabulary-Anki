import com.microsoft.edge.seleniumtools.EdgeDriver;
import com.microsoft.edge.seleniumtools.EdgeDriverService;
import com.microsoft.edge.seleniumtools.EdgeOptions;
import com.opencsv.CSVWriter;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, UnsupportedOSException {

        FileWriter file = new FileWriter(LocalDateTime.now() +".csv");
        CSVWriter writer = new CSVWriter(file);
        List<String[]> data = new ArrayList<>();

        String href = "https://www.essentialenglish.review/4000-essential-english-words-6/";
        String link;
        try{
            link = args[0]!=null?args[0]:href;
        }catch (ArrayIndexOutOfBoundsException exception){
            link = href;
        }
//        Navigate to book
        OsCheck.OSType ostype=OsCheck.getOperatingSystemType();
        switch (ostype) {
            case Windows -> System.setProperty("webdriver.edge.driver", "src/main/resources/msedgedriver.exe");
            case MacOS -> System.setProperty("webdriver.edge.driver", "src/main/resources/msedgedriver");
            case Linux -> System.setProperty("webdriver.edge.driver", "src/main/resources/msedgedriver-linux");
            case Other -> throw new UnsupportedOSException("Not support OS");
        }
        EdgeDriver driver = new EdgeDriver();
        driver.get(link);
        driver.navigate();

//        Get list unit
        WebElement tableContent = driver.findElementById("table-of-contents");
        List<WebElement> units = tableContent.findElements(By.tagName("a"));
        ArrayList<String> unitsLink = new ArrayList<>();
        for (int i = 0; i < units.size() - 1; i++) {
            String unitLink = units.get(i).getAttribute("href");
            unitsLink.add(unitLink);
        }
//        Iterate link
        for (String unit : unitsLink) {
            driver.get(unit);
            driver.navigate();
            WebElement wordlist = driver.findElementByClassName("wordlist");
            List<WebElement> words = wordlist.findElements(By.tagName("li"));
            for (WebElement word : words) {
                String image = convertImage(word.findElement(By.tagName("img")).getAttribute("src"));
                System.out.println(image);
                String[] item = word.findElement(By.className("en-word")).getText().split(" ");
                String voca = converColor(item[0]);
                System.out.println(voca);
                String pronoun;
                try{
                    pronoun = refactorContent(item[1] + " " + item[2]);
                }catch (ArrayIndexOutOfBoundsException exception){
                    try{
                        pronoun = refactorContent(item[1]);
                    }catch (ArrayIndexOutOfBoundsException exception1){
                        pronoun = "";
                    }
                }
                String finfo;
                try{
                    finfo = word.findElement(By.className("en-desc")).getText();
                }catch (NoSuchElementException exception){
                    finfo = "";
                }
                String linfo = word.findElement(By.className("en-exam")).getText();
                String info = refactorContent(finfo+"<br>"+linfo);
                String audio = embedHtmlAudio(word.findElement(By.tagName("img")).getAttribute("src").replaceAll("jpg","mp3"));
                System.out.println(audio);

                data.add(new String[]{voca, image, info, pronoun +"\n"+audio, "y" });
            }
        }
        writer.writeAll(data);
        writer.close();


    }
    private static String convertImage(String image) {
        return "<img src=\""+image+"\" style=\"border-radius: 300px;box-shadow: 0px 1px 20px rgb(0 0 0 / 20%);padding:0px;\">";
//        return image.replaceAll("img","img style=\"border-radius: 300px;box-shadow: 0px 1px 20px rgb(0 0 0 / 20%);padding:0px;\"");
    }
    private static String converColor(String trim) {
        return "<p style=\"margin:2px;color: rgb(98, 227, 164);font-weight:bold;\">"+trim+"</p>";
    }
    private static String refactorContent(String toString) {
        return "<p style=\"margin:2px\">"+toString+"</p>";
    }
    private static String embedHtmlAudio(String link) {
        return """
                <audio controls autoplay>
                  <source src="" type="audio/mpeg">
                Your browser does not support the audio element.
                </audio>""".replaceAll("src=\"\"","src=\""+link+"\"");
    }
}
