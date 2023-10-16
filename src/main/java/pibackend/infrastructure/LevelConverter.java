package pibackend.infrastructure;

import pibackend.domain.auth.role.model.entity.Level;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.List;

@Converter
public class LevelConverter implements AttributeConverter<List<Level>, String> {


    @Override
    public String convertToDatabaseColumn(List<Level> levels) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Level level : levels) {
            stringBuilder.append(level);
            stringBuilder.append(",");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    @Override
    public List<Level> convertToEntityAttribute(String s) {
        return Arrays.stream(s.split(","))
                .filter(x -> !x.isBlank())
                .map(Level::valueOf)
                .toList();
    }
}
