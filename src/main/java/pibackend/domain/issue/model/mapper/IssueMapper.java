package pibackend.domain.issue.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import pibackend.domain.issue.model.entity.Issue;
import pibackend.domain.issue.model.view.IssueView;

@Mapper
public interface IssueMapper {

    IssueView toView(Issue entity);

    Issue toEntity(IssueView view);

    Issue toEntity(@MappingTarget Issue entity, IssueView view);

}
