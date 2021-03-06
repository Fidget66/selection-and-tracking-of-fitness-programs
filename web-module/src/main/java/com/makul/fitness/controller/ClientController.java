package com.makul.fitness.controller;

import com.makul.fitness.dto.*;
import com.makul.fitness.service.api.ClientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/client/category/fitness")
    public String viewCategory(Model model){
        List<CategoryOfFitnessProgramDto> categories = clientService.getCategoryOfFitnessProgram();
        model.addAttribute("all", categories);
        return "client/categoryOfFitness";
    }

    @GetMapping("/client/category/fitness/{id}/program")
    public String viewFitnessProgram(@PathVariable ("id") long id, Model model,  HttpServletRequest request){
        List<FitnessProgramDto> programs = clientService.getListFitnessProgram(id);
        model.addAttribute("allPrograms", programs);
        model.addAttribute("req",request.getHeader("Referer"));
        return "client/fitnessPrograms";
    }

    @GetMapping("/client/category/fitness/program/{id}/description")
    public String viewFitnessProgramDescription(@PathVariable ("id") long programId, Model model,
                                                HttpServletRequest request){
        FitnessProgramDto program = clientService.getFitnessProgram(programId);
        model.addAttribute("fitProg", program);
        model.addAttribute("req",request.getHeader("Referer"));
        return "client/fitnessProgramDescription";
    }

    @GetMapping("/client/fitness/program/restrictions")
    public String returnFormWithRestriction(Model model){
        model.addAttribute("category",clientService.getCategoryOfFitnessProgram());
        return "client/fitnessProgramAddRestriction";
    }

    @PostMapping("/client/fitness/program/restrictions/duration")
    public String viewFitnessProgramWithRestrictions(FiltredDto filtredDto, Model model,
                                                     HttpServletRequest request){
        List<FitnessProgramDto> programList = clientService.getFitnessProgramWithRestrictions(filtredDto);
        model.addAttribute("allPrograms", programList);
        model.addAttribute("bookmarkMarker",true);
        model.addAttribute("req",request.getHeader("Referer"));
        return "client/fitnessPrograms";
    }

    @GetMapping("/client/fitness/program/{id}/bookmark")
    public String addBookmark(@PathVariable ("id") long programId){
        clientService.addToBookMarks(programId);
        return "redirect:/client/bookmarks";
    }

    @GetMapping("/client/account")
    public String returnClientAccount(Model model){
        return "client/clientAccount";
    }

    @GetMapping("/client/bookmarks")
    public String returnBookmarks(Model model, HttpServletRequest request){
        List<BookmarkDto> bookmarks = clientService.getBookmarks();
        model.addAttribute("allBookmark", bookmarks);
        model.addAttribute("req",request.getHeader("Referer"));
        return "client/clientBookmarks";
    }

    @GetMapping("/client/bookmark/{id}")
    public String removeBookmark(@PathVariable ("id") long bookmarkId){
        clientService.deleteBookmark(bookmarkId);
        return "redirect:/client/bookmarks";
    }

    @GetMapping("/client/program/complited")
    public String returnComplitedPrograms (Model model, HttpServletRequest request){
        List<ActiveProgramDto> activePrograms = clientService.getComplitedActivePrograms();
        model.addAttribute("allComplActProgr", activePrograms);
        model.addAttribute("req",request.getHeader("Referer"));
        return "client/complitedPrograms";
    }

    @GetMapping("/client/program/{id}/review")
    public String createReview (@PathVariable ("id") long fitnessProgrammId, Model model){
        model.addAttribute("fitnessId", fitnessProgrammId);
        return "client/review";
    }

    @PostMapping("/client/review/{fitnessId}")
    public String saveReview (@PathVariable ("fitnessId") long fitnessProgramId, ReviewDto review, Model model){
        clientService.addReview(fitnessProgramId,review);
        return "redirect:/client/account";
    }

    @GetMapping("/client/program/active")
    public String viewUserActiveProgram (Model model, HttpServletRequest request){
        ActiveProgramDto activeProgramDto = clientService.readUserActiveProgram();
        model.addAttribute("actProgr", activeProgramDto);
        model.addAttribute("req",request.getHeader("Referer"));
        return "client/activeProgram";
    }

    @GetMapping("/client/program/active/{id}/schedule/new/{count}")
    public String addDaysOfExercises (@PathVariable ("id") long activeId, @PathVariable ("count") int count,
                                      Model model, HttpServletRequest request){
        model.addAttribute("activeId", activeId);
        model.addAttribute("count", count);
        model.addAttribute("daysOfWeek",clientService.getDaysOfWeek());
        model.addAttribute("req",request.getHeader("Referer"));
        return "client/chooseExerciseDays";
    }


    @PostMapping("/client/program/active/{activeId}/schedule")
    public String createSchedule (@PathVariable ("activeId") long activeId, @ModelAttribute DayListDto dayList){
        clientService.createSchedule(activeId,dayList.getDaysOfweek());
        return "redirect:/client/program/active";
    }

    @GetMapping("/client/program/active/{activeId}/schedule")
    public String viewSchedule(@PathVariable("activeId") long activeId, Model model, HttpServletRequest request){
        List<ExerciseScheduleDto> scheduleList = clientService.getSchedule(activeId);
        model.addAttribute("exerciseList", scheduleList);
        model.addAttribute("activeId", activeId);
        model.addAttribute("req",request.getHeader("Referer"));
        return "client/exerciseSchedule";
    }

    @GetMapping ("/client/program/active/{activeId}/schedule/{id}")
    public String updateExercise (@PathVariable ("activeId") long activeId, @PathVariable ("id") long exerciseId){
        clientService.updateSchedule(exerciseId);
        return "redirect:/client/program/active/" + activeId + "/schedule";
    }

    @GetMapping("/client/fitness/program/{id}/active/program")
    public String chooseFitnessProgramAsActive(@PathVariable ("id") long fitnessProgramId){
        clientService.addActiveProgram(fitnessProgramId);
        return "redirect:/client/program/active";
    }

    @GetMapping("/client/category/fitness/program/{id}/reviews")
    public String viewReviews(@PathVariable("id") long fitnessProgramId, Model model, HttpServletRequest request){
        List<ReviewDto> reviewList = clientService.readReviews(fitnessProgramId);
        model.addAttribute("reviewList", reviewList);
        model.addAttribute("req",request.getHeader("Referer"));
        return "client/reviews";
    }

    @ModelAttribute("reviewDto")
    private ReviewDto prepareReviewModel() {
        return new ReviewDto();
    }
    @ModelAttribute("dayList")
    private DayListDto prepareListOfDays() {
        return new DayListDto();
    }
    @ModelAttribute("filtredDto")
    private FiltredDto prepareFiltredDto() {
        return new FiltredDto();
    }
}
