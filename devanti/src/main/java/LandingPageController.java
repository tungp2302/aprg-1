@Controller
public class LandingPageController {
    @GetMapping("/")
    public String landingPage(Model model) {
        model.addAttribute("welcomeMessage", "Schön, dass du hier bist!");

        List<User> users = List.of(
                new User("Navid", "navid@example.com"),
                new User("Adnan", "adnan@example.com")
        );
        model.addAttribute("users", users);

        return "landing_page";
    }

    static class User {
        private String name;
        private String email;

        public User(String name, String email) {
            this.name = name;
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }
    }
}
