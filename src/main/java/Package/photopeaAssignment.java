package Package;

import com.microsoft.playwright.*;
import java.nio.file.*;

public class photopeaAssignment {

    public static void main(String[] args) {

        Path exportDir = Paths.get("exports");
        try {
            Files.createDirectories(exportDir);
        } catch (Exception e) {
            System.out.println("Export folder already exists");
        }

        try (Playwright playwright = Playwright.create()) {

            Browser browser = playwright.chromium()
                    .launch(new BrowserType.LaunchOptions().setHeadless(false));

            BrowserContext context = browser.newContext(
                    new Browser.NewContextOptions().setAcceptDownloads(true)
            );

            Page page = context.newPage();

            // Open Photopea
            page.navigate("https://www.photopea.com/");
            page.waitForTimeout(6000);
            System.out.println("Photopea opened");

            // Draw shapes
            page.keyboard().press("U"); // Shape tool

            // Rectangle
            page.mouse().move(200, 200);
            page.mouse().down();
            page.mouse().move(600, 400);
            page.mouse().up();
            System.out.println("Rectangle created");

            // Circle
            page.mouse().move(650, 200);
            page.mouse().down();
            page.mouse().move(800, 350);
            page.mouse().up();
            System.out.println("Circle created");

            // Triangle
            page.mouse().move(400, 450);
            page.mouse().down();
            page.mouse().move(550, 600);
            page.mouse().up();
            System.out.println("Triangle created");

            // Add Text
            page.keyboard().press("T");
            page.mouse().click(700, 300);
            page.keyboard().type("Hello");
            page.mouse().click(700, 450);
            page.keyboard().type("World");
            System.out.println("Text added");

            // Drag & Drop
            page.mouse().move(350, 300);
            page.mouse().down();
            page.mouse().move(650, 450);
            page.mouse().up();
            System.out.println("Drag and drop completed");

            // Load external image
            page.navigate("https://www.photopea.com#%7B%22files%22:[%22https://upload.wikimedia.org/wikipedia/commons/3/3a/Cat03.jpg%22]%7D");
            page.waitForTimeout(6000);
            System.out.println("Image loaded");

            // Remove background
            page.keyboard().press("Control+Shift+R");
            page.waitForTimeout(5000);
            System.out.println("Background removed");

            
        }
    }
}