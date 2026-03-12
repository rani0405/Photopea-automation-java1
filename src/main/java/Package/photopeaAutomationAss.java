package Package;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.io.IOException;
import java.nio.file.*;

public class photopeaAutomationAss {

    public static void main(String[] args) throws IOException {
        Path exportDir = Paths.get("exports");

        // Create exports folder if it doesn't exist
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
            page.setViewportSize(1400, 900);

            // Open Photopea
            page.navigate("https://www.photopea.com/");
            page.waitForTimeout(6000);
            System.out.println("Photopea opened");

            // ---------------- SHAPES ----------------
            page.keyboard().press("U"); // Shape tool

            // Rectangle (RED)
            page.mouse().move(200, 200);
            page.mouse().down();
            page.mouse().move(600, 400);
            page.mouse().up();
            setFillColor(page, "#FF0000");
            System.out.println("Rectangle created (Red)");

            // Circle (BLUE)
            page.mouse().move(650, 200);
            page.mouse().down();
            page.mouse().move(820, 350);
            page.mouse().up();
            setFillColor(page, "#0000FF");
            System.out.println("Circle created (Blue)");

            // Triangle (GREEN)
            page.mouse().move(400, 450);
            page.mouse().down();
            page.mouse().move(550, 600);
            page.mouse().up();
            setFillColor(page, "#00FF00");
            System.out.println("Triangle created (Green)");

            // ---------------- TEXT ----------------
            page.keyboard().press("T"); // Text tool

            // HELLO
            page.mouse().click(700, 300);
            page.keyboard().type("Hello");
            page.fill("input[title='Font Size']", "120");
            page.click("div[title='Text Color']");
            page.fill("input[type='color']", "#FFFFFF");
            System.out.println("Hello text added");

            // WORLD
            page.mouse().click(760, 420);
            page.keyboard().type("World");
            page.fill("input[title='Font Size']", "150");
            page.click("div[title='Text Color']");
            page.fill("input[type='color']", "#FFFFFF");
            System.out.println("World text added");

            // ---------------- DRAG & DROP ----------------
            page.mouse().move(350, 300);
            page.mouse().down();
            page.mouse().move(650, 450);
            page.mouse().up();
            System.out.println("Drag and drop completed");

            // ---------------- LOAD IMAGE ----------------
            page.navigate(
                    "https://www.photopea.com#%7B%22files%22:[%22https://images.pexels.com/photos/45201/keyboard-white-computer-keys-45201.jpeg%22]%7D"
            );
            page.waitForTimeout(6000);
            System.out.println("Pexels keyboard image loaded");

            // Remove background
            page.keyboard().press("Control+Shift+R");
            page.waitForTimeout(5000);
            System.out.println("Background removed");

            // ---------------- PREVIEW SCREENSHOT ----------------
            Path previewPath = exportDir.resolve("preview.png");
            page.screenshot(
                    new Page.ScreenshotOptions()
                            .setPath(previewPath)
                            .setTimeout(60000)
            );
            System.out.println("Preview screenshot saved");

            // ---------------- EXPORT PNG ----------------
            Download pngDownload = page.waitForDownload(() -> {
                page.keyboard().press("Alt+F");
                page.waitForTimeout(200);
                page.keyboard().press("E");
                page.waitForTimeout(200);
                page.keyboard().press("P");
            });
            Path pngPath = exportDir.resolve("output.png");
            pngDownload.saveAs(pngPath);
            System.out.println("PNG Exported");

            // ---------------- EXPORT JPG ----------------
            Download jpgDownload = page.waitForDownload(() -> {
                page.keyboard().press("Alt+F");
                page.waitForTimeout(200);
                page.keyboard().press("E");
                page.waitForTimeout(200);
                page.keyboard().press("J");
            });
            Path jpgPath = exportDir.resolve("output.jpg");
            jpgDownload.saveAs(jpgPath);
            System.out.println("JPG Exported");

            // ---------------- SAVE PSD ----------------
            Download psdDownload = page.waitForDownload(() -> {
                page.keyboard().press("Alt+F");
                page.waitForTimeout(200);
                page.keyboard().press("S");
            });
            Path psdPath = exportDir.resolve("output.psd");
            psdDownload.saveAs(psdPath);
            System.out.println("PSD Saved");

            // ---------------- VALIDATION ----------------
            System.out.println("\n----- VALIDATION CHECKS -----");
            if (Files.exists(pngPath)) System.out.println("✔ PNG file downloaded");
            if (Files.exists(jpgPath)) System.out.println("✔ JPG file downloaded");
            if (Files.exists(psdPath)) System.out.println("✔ PSD file downloaded");
            if (Files.exists(previewPath)) System.out.println("✔ Preview screenshot exists");

            // File size check
            System.out.println("\n----- FILE SIZE CHECK -----");
            System.out.println("PNG size: " + Files.size(pngPath));
            System.out.println("JPG size: " + Files.size(jpgPath));
            if (Files.size(pngPath) > 0) System.out.println("✔ PNG valid");
            if (Files.size(jpgPath) > 0) System.out.println("✔ JPG valid");
            System.out.println("-----------------------------");

            page.waitForTimeout(3000);
            browser.close();
        }
    }

    // ---------------- HELPER METHOD ----------------
    private static void setFillColor(Page page, String colorHex) {
        // Wait until any frame has the Fill button (up to 30s)
        Frame editorFrame = null;
        long startTime = System.currentTimeMillis();
        long timeout = 30000; // 30 seconds

        while (System.currentTimeMillis() - startTime < timeout) {
            for (Frame f : page.frames()) {
                if (f.locator("div[title='Fill']").count() > 0) {
                    editorFrame = f;
                    break;
                }
            }
            if (editorFrame != null) break;
            page.waitForTimeout(500); // wait 0.5s before retry
        }

        if (editorFrame == null) {
            throw new RuntimeException("Could not find the Photopea editor frame");
        }

        // Wait for Fill button
        Locator fillButton = editorFrame.locator("div[title='Fill']");
        fillButton.waitFor(new Locator.WaitForOptions()
                .setTimeout(10000)
                .setState(WaitForSelectorState.VISIBLE));
        fillButton.click();

        // Fill color input
        Locator colorInput = editorFrame.locator("input[type='color']");
        colorInput.waitFor(new Locator.WaitForOptions()
                .setTimeout(10000)
                .setState(WaitForSelectorState.VISIBLE));
        colorInput.fill(colorHex);

        // Press Enter inside the frame
        //editorFrame.keyboard().press("Enter");
    }
}