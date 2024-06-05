package com.gymbro.GymBro.controllers;

import com.gymbro.GymBro.models.Exercise;
import com.gymbro.GymBro.models.Workout;
import com.gymbro.GymBro.models.WorkoutPlan;
import com.gymbro.GymBro.services.ExerciseService;
import com.gymbro.GymBro.services.UserService;
import com.gymbro.GymBro.services.WorkoutPlanService;
import com.gymbro.GymBro.services.WorkoutService;
import com.gymbro.GymBro.web.DTO.ExerciseDto;
import com.gymbro.GymBro.web.DTO.WorkoutPlanDto;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
public class WorkoutPlanController {
    private final WorkoutPlanService workoutPlanService;
    private final ExerciseService exerciseService;
    private final UserService userService;
    private final WorkoutService workoutService;


    public WorkoutPlanController(WorkoutPlanService workoutPlanService, ExerciseService exerciseService, UserService userService, WorkoutService workoutService) {
        this.workoutPlanService = workoutPlanService;
        this.exerciseService = exerciseService;
        this.userService = userService;
        this.workoutService = workoutService;
    }

    @GetMapping("workoutplans")
    public String getWorkoutPlans(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("workout_plans", workoutPlanService.findAllWorkoutPlansDtoOfUser(user));

        return "workoutplans";
    }

    @GetMapping("addworkoutplan")
    public String showAddWorkoutPlanForm(Model model, @AuthenticationPrincipal User user) {
        WorkoutPlanDto workoutPlanDto = new WorkoutPlanDto();

        List<ExerciseDto> exercises = exerciseService.findAllExercisesOfUser(user);

        if (exercises.isEmpty()) {
            return "redirect:/workoutplans?addfail";
        }

        model.addAttribute("exercisesDto", exercises);

        return "addworkoutplan";
    }

    @PostMapping("addworkoutplan/save")
    public String saveWorkoutPlan(@RequestParam Map<String, String> params,
                                  Model model,
                                  @AuthenticationPrincipal User user) {

        return handleWorkoutPlan(params, model, user, null, "addworkoutplan", "redirect:/workoutplans?addsuccess");
    }

    @GetMapping("workoutplans/edit/{id}")
    public String showEditWorkoutPlanForm(@PathVariable Long id, Model model, @AuthenticationPrincipal User user) {
        WorkoutPlan workoutPlan = workoutPlanService.findWorkoutPlanById(id);
        WorkoutPlanDto workoutPlanDto = workoutPlanService.mapToWorkoutPlanDto(workoutPlan);

        Long userId = userService.findUserByName(user.getUsername()).getId();
        if (!Objects.equals(userId, workoutPlanDto.getUserId())) {
            return "redirect:/workoutplans";
        }

        model.addAttribute("workoutPlan", workoutPlanDto);
        model.addAttribute("exercisesDto",
                exerciseService.findAllExercisesOfUser(user));

        return "editworkoutplan";
    }

    @PutMapping("workoutplans/edit/{id}")
    public String editWorkoutPlan(@PathVariable Long id,
                                  @RequestParam Map<String, String> params,
                                  Model model,
                                  @AuthenticationPrincipal User user) {

        Long userId = userService.findUserByName(user.getUsername()).getId();
        Long workoutPlanUserId = workoutPlanService.findWorkoutPlanById(id).getUser().getId();

        if (!Objects.equals(userId, workoutPlanUserId)) {
            return "redirect:/workoutplans";
        }

        return handleWorkoutPlan(params, model, user, id, "editworkoutplan", "redirect:/workoutplans?editsuccess");
    }

    @DeleteMapping("workoutplans/delete/{id}")
    public String deleteWorkoutPlan(@PathVariable Long id, @AuthenticationPrincipal User user) {

        Long userId = userService.findUserByName(user.getUsername()).getId();

        WorkoutPlan workoutPlan = workoutPlanService.findWorkoutPlanById(id);
        Long workoutPlanUserId = workoutPlan.getUser().getId();

        if (!Objects.equals(userId, workoutPlanUserId)) {
            return "redirect:/workoutplans";
        }

        List<Workout> workouts = workoutService.findByWorkoutPlan(workoutPlan);
        if (!workouts.isEmpty()) {
            return "redirect:/workoutplans?deletefail";
        }

        workoutPlanService.deleteWorkoutPlanById(id);

        return "redirect:/workoutplans?deletesuccess";
    }

    private String handleWorkoutPlan(Map<String, String> params, Model model, User user, Long workoutPlanId, String errorView, String successRedirect) {
        WorkoutPlanDto workoutPlanDto = new WorkoutPlanDto();

        String name = params.get("name");
        if (name == null || name.isEmpty()) {
            return setupErrorModel(model, user, "Name is required!", errorView, workoutPlanId);
        }

        List<Long> exerciseIds = new ArrayList<>();
        List<Integer> sets = new ArrayList<>();
        List<Integer> reps = new ArrayList<>();

        parseParams(params, exerciseIds, sets, reps);

        if (exerciseIds.isEmpty() || sets.isEmpty() || reps.isEmpty() || reps.size() != sets.size() || sets.size() != exerciseIds.size()) {
            return setupErrorModel(model, user, "Exercises form wasn't completed correctly!", errorView, workoutPlanId);
        }

        workoutPlanDto.setSets(sets);
        workoutPlanDto.setReps(reps);
        workoutPlanDto.setName(name);

        Long userId = userService.findUserByName(user.getUsername()).getId();
        workoutPlanDto.setUserId(userId);

        List<ExerciseDto> exerciseDtoList = exerciseIds.stream()
                .map(exerciseService::findExerciseById)
                .map(exerciseService::mapToExerciseDto)
                .toList();
        workoutPlanDto.setExercises(exerciseDtoList);

        if (workoutPlanId == null) {
            workoutPlanService.saveWorkoutPlan(workoutPlanDto);
        } else {
            workoutPlanService.saveWorkoutPlan(workoutPlanDto, workoutPlanId);
        }

        return successRedirect;
    }

    private void parseParams(Map<String, String> params, List<Long> exerciseIds, List<Integer> sets, List<Integer> reps) {
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String element = entry.getKey().substring(0, entry.getKey().length() - 1);
            String val = entry.getValue();

            if (!val.isEmpty() && val.length() < "exercisesIds".length()) {
                switch (element) {
                    case "exercisesIds" -> exerciseIds.add(Long.parseLong(val));
                    case "sets" -> sets.add(Integer.parseInt(val));
                    case "reps" -> reps.add(Integer.parseInt(val));
                }
            }
        }
    }

    private String setupErrorModel(Model model, User user, String errorMessage, String viewName, Long workoutPlanId) {
        model.addAttribute("errorName", errorMessage);
        model.addAttribute("exercisesDto", exerciseService.findAllExercisesOfUser(user));
        model.addAttribute("workoutPlan",
                workoutPlanService.mapToWorkoutPlanDto(workoutPlanService.findWorkoutPlanById(workoutPlanId))
        );
        return viewName;
    }
}
