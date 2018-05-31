package main;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/*
Под мудрым руководством товарища Сталина, подготовь Красную Армию к наступлению.
(править можно только то, на что указал товарищ Сталин)
 */
public class HashMapCounterspionage {

    private static final Random random = new Random();

    static class Abwehr {
        private static List<Unterscharfuhrer> abwehrOfficers = new ArrayList<>();
        private  static int killedN = 0;
        static {
            List<String> names = List.of("ABRAHAM", "EUGEN", "FERDINAND", "GERHOLD");
            List<String> lastNames = List.of("KLEIN", "RICHTER", "WEBER", "SCHMIDT");
            IntStream.rangeClosed(1, 5).forEach((i) -> {
                abwehrOfficers.add(new Unterscharfuhrer(
                        lastNames.get(random.nextInt(lastNames.size())),
                        names.get(random.nextInt(names.size())),
                        random.nextInt(37) + 18
                ));
            });
        }

        static void infiltrate(List<Comrade> redArmy) {
            Iterator<Comrade> iterator = redArmy.iterator();
            abwehrOfficers.forEach(officer -> {
                if (random.nextBoolean()) {
                    redArmy.add(random.nextInt(redArmy.size()), officer.disguiseWithForgedTicket());
                } else {
                    Comrade killed = redArmy.remove(random.nextInt(redArmy.size()));
                    killedN++;
                    redArmy.add(random.nextInt(redArmy.size()), officer.disguiseWithRealTicket(killed));
                }
            });

        }
    }

    static class NKVD {
        private static Map<ComradePartyTicket, Comrade> comrades = new HashMap<>();

        static {
            List<String> names = List.of("ИВАН", "ДМИТРИЙ", "ВАСИЛИЙ", "ФЕОФАН", "ИЛЬЯ", "ПЕТР", "ВЛАДИМИР", "ГЛЕБ");
            List<String> lastNames = List.of("ИВАНОВ", "ПЕТРОВ", "САМОЙЛОВ", "ДАВЫДОВ", "СЕМЕНОВ", "ВЫКРУТАСОВ");
            List<String> patronymics = List.of("ИВАНОВИЧ", "ПЕТРОВИЧ", "ИЛЬИЧ", "ВЛАДИМИРОВИЧ");
            IntStream.rangeClosed(1, 50).forEach((i) -> {
                String name = names.get(random.nextInt(names.size()));
                String lastName = lastNames.get(random.nextInt(lastNames.size()));
                String patronymic = patronymics.get(random.nextInt(patronymics.size()));
                Comrade comrade = new Comrade(name, lastName, patronymic,
                        new ComradePartyTicket(i,
                                lastName + " " + name + " " + patronymic,
                                random.nextInt(37) + 18,
                                ComradePartyTicket.BracketsMaterial.IRON));
                comrades.put(comrade.ticket.archiveCopy(), comrade);
            });
        }

        static List<Comrade> findComrades(List<ComradePartyTicket> partyTickets) {
            return partyTickets.stream().map(ticket -> comrades.get(ticket)).filter(Objects::nonNull).collect(Collectors.toList());
        }

        static List<ComradePartyTicket> findSuspicious(List<Comrade> redArmy) {
            return Stream.concat(
                    redArmy.stream().filter(comrade -> !comrades.getOrDefault(comrade.ticket, comrade).equals(comrade) || !checkTicketValid(comrade.ticket)),
                    redArmy.stream().filter(comrade -> random.nextBoolean() && random.nextBoolean() && random.nextBoolean() && random.nextBoolean()))          // TODO: Доказать, что не верблюд
                    .distinct()
                    .map(comrade -> comrade.ticket.archiveCopy())
                    .collect(Collectors.toList());
        }
        static List<Comrade> killTraitors(List<Comrade> redArmy, List<ComradePartyTicket> traitorTickets) {
            List<Comrade> traitors = redArmy.stream()
                    .filter(comrade -> traitorTickets.contains(comrade.ticket))
                    .collect(Collectors.toList());
            redArmy.removeAll(traitors);
            return traitors;

        }

        static boolean checkTicketValid(ComradePartyTicket ticket) {
            return ticket != null && ticket.bracketsMaterial == ComradePartyTicket.BracketsMaterial.IRON;
        }
    }

    static class SMERSH {
        public static List<ComradePartyTicket> detectSpies(List<Comrade> redArmy, List<ComradePartyTicket> suspicious) {
            return Stream
                    .concat(
                            redArmy.stream()
                                    .filter(comrade -> suspicious.contains(comrade.ticket))
                                    .filter(s ->
                                            !s.ticket.fio.contains(s.lastName) ||
                                                    !s.ticket.fio.contains(s.name) ||
                                                    !s.ticket.fio.contains(s.patronymic)),
                            redArmy.stream()
                                    .filter(comrade -> suspicious.contains(comrade.ticket))
                                    .filter(s -> s.ticket.bracketsMaterial != ComradePartyTicket.BracketsMaterial.IRON))
                    .distinct()
                    .map(comrade -> comrade.ticket.archiveCopy())
                    .collect(Collectors.toList());
        }
    }

    static class ComradePartyTicket {
        // Stalin thinks ComradePartyTicket could be better

        int N;
        String fio;
        int age;
        BracketsMaterial bracketsMaterial;
        boolean copy = false;

        public ComradePartyTicket(int n, String fio, int age, BracketsMaterial bracketsMaterial) {
            N = n;
            this.fio = fio;
            this.age = age;
            this.bracketsMaterial = bracketsMaterial;
        }

        public ComradePartyTicket archiveCopy() {
            ComradePartyTicket copy = new ComradePartyTicket(N, fio, age, bracketsMaterial);
            copy.copy = true;
            return copy;
        }

        public enum BracketsMaterial {
            IRON,
            STAINLESS_STEEL
        }

        public String toString() {
            String result = "";
            result += N + " ";
            result += fio + " ";
            result += age + " ";
            result += bracketsMaterial + " ";
            result += copy + " ";
            return  result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }

            ComradePartyTicket other = (ComradePartyTicket) obj;

            return N == other.N && fio.equals(other.fio) && age == other.age && bracketsMaterial == other.bracketsMaterial;
        }

        @Override
        public int hashCode() {
            return N;
        }
    }

    static class Comrade {
        // Stalin thinks You should watch Comarades

        String name;
        String lastName;
        String patronymic;

        ComradePartyTicket ticket;

        public Comrade(String name, String lastName, String patronymic, ComradePartyTicket ticket) {
            this.name = name;
            this.lastName = lastName;
            this.patronymic = patronymic;
            this.ticket = ticket;
        }
    }

    static class Unterscharfuhrer {
        // Stalin said never forget about your enemies

        String name;
        String lastName;

        int age;

        public Unterscharfuhrer(String name, String lastName, int age) {
            this.name = name;
            this.lastName = lastName;
            this.age = age;
        }

        public Comrade disguiseWithRealTicket(Comrade killedComrade) {
            return new Comrade(name, lastName, "NACI", killedComrade.ticket);
        }

        public Comrade disguiseWithForgedTicket() {
            return new Comrade(name, lastName, "",
                    new ComradePartyTicket(
                            random.nextInt(199) + 100,
                            name + " " + lastName,
                            age,
                            ComradePartyTicket.BracketsMaterial.STAINLESS_STEEL));
        }
    }

    public static void main(String[] args) {
        ArrayList<Comrade> redArmy = new ArrayList<>(NKVD.comrades.values());
        Abwehr.infiltrate(redArmy);
        List<ComradePartyTicket> spies = NKVD.findSuspicious(redArmy);
        List<ComradePartyTicket> traitors = SMERSH.detectSpies(redArmy, spies);
        List<Comrade> killedComrades = NKVD.findComrades(traitors);
        List<Comrade> killedTraitors = NKVD.killTraitors(redArmy, traitors);

        if (killedComrades.size() == Abwehr.killedN && killedTraitors.size() == Abwehr.abwehrOfficers.size()) {
            System.out.println("Внимание! Внимание! Говорит Москва! Красная Армия перешла в наступление!");
            System.out.println(String.format("Предательски в спину были убиты бойцы: %s", killedComrades.stream().map(s -> s.ticket.fio).collect(Collectors.joining("\n"))));
            System.out.println(String.format("Но храбрые бойцы НКВД не спят! Раскрыта и уничтожена вражеская группа в составе: %s", killedTraitors.stream().map(traitor -> traitor.lastName + " " + traitor.name).collect(Collectors.joining("\n"))));
            System.out.println(String.format("Героическая Советская армия в составе %s \nведет бой с врагом", redArmy.stream().map(s -> s.ticket.fio).collect(Collectors.joining("\n"))));
        } else {
            System.out.println("На фронте без перемен");
            if (!killedComrades.isEmpty()) {
                System.out.println(String.format("Предательски в спину были убиты бойцы: %s", killedComrades.stream().map(s -> s.ticket.fio).collect(Collectors.joining("\n"))));
            }
            if (!killedTraitors.isEmpty()) {
                System.out.println(String.format("Но храбрые бойцы НКВД не спят! Раскрыта и уничтожена вражеская группа в составе: %s", killedTraitors.stream().map(traitor -> traitor.lastName + " " + traitor.name).collect(Collectors.joining("\n"))));
            }
            if (killedComrades.size() < Abwehr.killedN) {
                System.out.println(String.format("В лесу найдено %d неопознанных тела", Abwehr.killedN - killedComrades.size()));
            }
            System.out.println(String.format("Героическая Советская армия в составе:\n %s \n\nведет неравный бой с врагом", redArmy.stream().map(s -> s.ticket.fio).collect(Collectors.joining("\n"))));
        }
    }
}
