package lambdasinaction.chap10;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class OptionalMain {

    public String getCarInsuranceName(Optional<Person> person) {
        return person.flatMap(Person::getCar)
                .flatMap(Car::getInsurance)
                .map(Insurance::getName)
                .orElse("Unknown");
    }

}
