package com.usermanagment.infrastructure.email;

public class EmailBuilder {

    public static String buildEmail(String name, String link) {
        return "<div style=\"font-family: Arial, sans-serif; font-size: 16px; margin: 0; color: #333\">\n" +
                "<div style=\"background-color: #f8f8f8; padding: 20px\">\n" +
                "<h1 style=\"color: #333; font-size: 24px; margin-bottom: 20px;\">Potwierdź swój adres e-mail</h1>\n" +
                "<p style=\"margin: 0 0 20px;\">Cześć " + name + ",</p>\n" +
                "<p style=\"margin: 0 0 20px;\">Dziękujemy za rejestrację. Prosimy kliknąć poniższy link, aby aktywować swoje konto:</p>\n" +
                "<p style=\"margin: 0 0 20px;\">\n" +
                "<a href=\"" + link + "\" style=\"color: #1e87f0; text-decoration: none;\">Aktywuj teraz</a>\n" +
                "</p>\n" +
                "<p style=\"margin: 0 0 20px;\">Link wygaśnie za 15 minut.</p>\n" +
                "</div>\n" +
                "</div>";
    }


}
