package com.makul.fitness.service;

import com.makul.fitness.dto.*;
import com.makul.fitness.exceptions.IncorrectNumberOfDaysException;
import com.makul.fitness.service.api.ClientService;
import com.makul.fitness.service.api.UsersSecurityService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class ClientServiceImpl implements ClientService {

    private final RestTemplate restTemplate;
    private final UsersSecurityService securityService;
    private final String baseURL = "http://fitnessApp:8124/fitnessDB-app/";
    //private final String baseURL = "http://localhost:8124/fitnessDB-app/";

    public ClientServiceImpl(RestTemplate restTemplate, UsersSecurityService securityService) {
        this.restTemplate = restTemplate;
        this.securityService = securityService;
    }

    @Override
    public List<CategoryOfFitnessProgramDto> getCategoryOfFitnessProgram() {
        List <CategoryOfFitnessProgramDto> categories = Arrays.asList(restTemplate.getForObject(baseURL +
                        "fitness/categories", CategoryOfFitnessProgramDto[].class));
        return categories;
    }

    @Override
    public List<FitnessProgramDto> getListFitnessProgram(long categoryId) {
        List <FitnessProgramDto> programs = Arrays.asList(restTemplate.getForObject(baseURL +
                        "category/"+ categoryId+ "/program/fitness", FitnessProgramDto[].class));
        return programs;
    }

    @Override
    public FitnessProgramDto getFitnessProgram(long programId) {
        FitnessProgramDto program = restTemplate.getForObject(baseURL+"program/fitness/" + programId,
                FitnessProgramDto.class);
        return program;
    }

    @Override
    public List<FitnessProgramDto> getFitnessProgramWithRestrictions(int duration) {
        List <FitnessProgramDto> programs = Arrays.asList(restTemplate.getForObject(baseURL +
                "user/" + getUserId() + "/program/fitness/"+duration, FitnessProgramDto[].class));
        return programs;
    }

    @Override
    public void addToBookMarks(long fitnessProgramId) {
        restTemplate.getForObject(baseURL+"user/"+ getUserId() + "/bookmark/" + fitnessProgramId,
                BookmarkDto.class);
    }

    @Override
    public List<BookmarkDto> getBookmarks() {
        List <BookmarkDto> bookmars = Arrays.asList(restTemplate.getForObject(baseURL +
                "user/" + getUserId() + "/bookmarks", BookmarkDto[].class));
        return bookmars;
    }

    @Override
    public void deleteBookmark(long bookmarkId) {
        restTemplate.delete(baseURL+"bookmark/"+bookmarkId);
    }

    @Override
    public List<ActiveProgramDto> getComplitedActivePrograms() {
        List<ActiveProgramDto> activePrograms = Arrays.asList(restTemplate.getForObject(baseURL + "user/"
                + getUserId() + "/programs/active", ActiveProgramDto[].class));
        for (ActiveProgramDto program:activePrograms) {
            if (Objects.nonNull(program.getFitnessProgram().getReviews())){
                int count = 0;
                for (ReviewDto reviewDto:program.getFitnessProgram().getReviews()){
                    if (reviewDto.getAuthorId()==getUserId()) {
                        program.getFitnessProgram().setReviews(List.of(reviewDto));
                        count++;
                    }
                }
                if (count==0) program.getFitnessProgram().getReviews().clear();
            }
        }
        return activePrograms;
    }

    @Override
    public void addReview(long fitnessProgramId, ReviewDto reviewDto) {
        reviewDto.setAuthorId(getUserId());
        restTemplate.postForLocation(baseURL+"user/program/fitness/" + fitnessProgramId + "/review",reviewDto);
    }

    @Override
    public ActiveProgramDto readUserActiveProgram() {
        ActiveProgramDto activeProgramDto = restTemplate
                .getForObject(baseURL+"/user/"+ getUserId() + "/program/active", ActiveProgramDto.class);
        return activeProgramDto;
    }

    @Override
    public void createSchedule(long activeProgramId, Set<String> days) {
        ActiveProgramDto activeProgramDto = restTemplate
                .getForObject(baseURL+"program/active/" + activeProgramId, ActiveProgramDto.class);
        if (activeProgramDto.getFitnessProgram().getExercisePerWeek()!=days.size())
            throw new IncorrectNumberOfDaysException();
        activeProgramDto.setDays("");
        for (String day: days) {
            activeProgramDto.setDays(activeProgramDto.getDays()+day+";");
        }
        restTemplate.put(baseURL + "program/fitness/schedule",activeProgramDto);
    }

    @Override
    public List<ExerciseScheduleDto> getSchedule(long activeProgramId) {
        List<ExerciseScheduleDto> scheduleDtoList =Arrays.asList( restTemplate.getForObject(baseURL +
                "program/active/" + activeProgramId + "/exercises", ExerciseScheduleDto[].class));
        return scheduleDtoList;
    }

    @Override
    public void updateSchedule(long exerciseId) {
        restTemplate.getForObject(baseURL + "schedule/exercise/" + exerciseId,
                ExerciseScheduleDto.class);
    }

    @Override
    public void addActiveProgram(long fitnessProgramId) {
        restTemplate.getForObject(baseURL + "user/" + getUserId() + "/program/active/" + fitnessProgramId,
                ActiveProgramDto.class);
    }

    @Override
    public List<ReviewDto> readReviews(long fitnessProgramId) {
        FitnessProgramDto program = restTemplate.getForObject(baseURL+"program/fitness/" + fitnessProgramId,
                FitnessProgramDto.class);
        return program.getReviews();
    }

    @Override
    public List <String> getDaysOfWeek(){
        List <String> daysOfWeek = List.of("MONDAY", "TUESDAY", "WEDNESDAY",
                "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY");
        return daysOfWeek;
    }

    private long getUserId(){
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        return securityService.readByLogin(login).getUserId();
    }
}
