package com.gymbro.GymBro.bootstrap;

import com.gymbro.GymBro.models.*;
import com.gymbro.GymBro.models.Set;
import com.gymbro.GymBro.repositories.ExerciseRepository;
import com.gymbro.GymBro.repositories.SetRepository;
import com.gymbro.GymBro.repositories.WorkoutPlanRepository;
import com.gymbro.GymBro.repositories.WorkoutRepository;
import com.gymbro.GymBro.services.*;
import com.gymbro.GymBro.web.DTO.ExerciseDto;
import com.gymbro.GymBro.web.DTO.SetDto;
import com.gymbro.GymBro.web.DTO.WorkoutPlanDto;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

@Component
public class BootstrapData implements CommandLineRunner {
    private final UserService userService;
    private final ExerciseRepository exerciseRepository;
    private final WorkoutPlanRepository workoutPlanRepository;
    private final WorkoutRepository workoutRepository;
    private final SetRepository setRepository;

    public BootstrapData(UserService userService, ExerciseRepository exerciseRepository,
                         WorkoutPlanRepository workoutPlanRepository, WorkoutRepository workoutRepository,
                         SetRepository setRepository) {
        this.userService = userService;
        this.exerciseRepository = exerciseRepository;
        this.workoutPlanRepository = workoutPlanRepository;
        this.workoutRepository = workoutRepository;
        this.setRepository = setRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Random random = new Random();

        UserEntity user = userService.findUserByName("test");

        if (user == null) {
            return;
        }

        // Plan A Exercises
        Exercise benchpress = new Exercise();
        benchpress.setName("bench press");
        benchpress.setDescription("");
        benchpress.setUser(user);
        benchpress = exerciseRepository.save(benchpress);

        Exercise larsenpress = new Exercise();
        larsenpress.setName("larsen press");
        larsenpress.setDescription("");
        larsenpress.setUser(user);
        larsenpress = exerciseRepository.save(larsenpress);

        Exercise barbellRow = new Exercise();
        barbellRow.setName("barbell row");
        barbellRow.setDescription("");
        barbellRow.setUser(user);
        barbellRow = exerciseRepository.save(barbellRow);

        Exercise dbShoulderPress = new Exercise();
        dbShoulderPress.setName("db shoulder press");
        dbShoulderPress.setDescription("");
        dbShoulderPress.setUser(user);
        dbShoulderPress = exerciseRepository.save(dbShoulderPress);

        Exercise latPullover = new Exercise();
        latPullover.setName("lat pullover");
        latPullover.setDescription("");
        latPullover.setUser(user);
        latPullover = exerciseRepository.save(latPullover);

        Exercise powellRaise = new Exercise();
        powellRaise.setName("powell raise");
        powellRaise.setDescription("");
        powellRaise.setUser(user);
        powellRaise = exerciseRepository.save(powellRaise);

        // Plan B Exercises
        Exercise inclBenchPress = new Exercise();
        inclBenchPress.setName("incline bench press");
        inclBenchPress.setDescription("");
        inclBenchPress.setUser(user);
        inclBenchPress = exerciseRepository.save(inclBenchPress);

        Exercise cableRow = new Exercise();
        cableRow.setName("cable row");
        cableRow.setDescription("");
        cableRow.setUser(user);
        cableRow = exerciseRepository.save(cableRow);

        Exercise lateralRaise = new Exercise();
        lateralRaise.setName("lateral raise");
        lateralRaise.setDescription("");
        lateralRaise.setUser(user);
        lateralRaise = exerciseRepository.save(lateralRaise);

        Exercise tricepsExtensions = new Exercise();
        tricepsExtensions.setName("triceps extensions");
        tricepsExtensions.setDescription("");
        tricepsExtensions.setUser(user);
        tricepsExtensions = exerciseRepository.save(tricepsExtensions);

        Exercise bicepsCurls = new Exercise();
        bicepsCurls.setName("biceps curls");
        bicepsCurls.setDescription("");
        bicepsCurls.setUser(user);
        bicepsCurls = exerciseRepository.save(bicepsCurls);

        // Plan A
        WorkoutPlan workoutPlanA = new WorkoutPlan();
        workoutPlanA.setName("Plan A");
        workoutPlanA.setExercises(Arrays.asList(benchpress, larsenpress, barbellRow, dbShoulderPress, latPullover, powellRaise));
        workoutPlanA.setSets(Arrays.asList(3, 4, 4, 3, 3, 2));
        workoutPlanA.setReps(Arrays.asList(3, 6, 8, 8, 10, 12));
        workoutPlanA.setUser(user);
        workoutPlanA = workoutPlanRepository.save(workoutPlanA);

        // Plan B
        WorkoutPlan workoutPlanB = new WorkoutPlan();
        workoutPlanB.setName("Plan B");
        workoutPlanB.setExercises(Arrays.asList(inclBenchPress, cableRow, lateralRaise, tricepsExtensions, bicepsCurls));
        workoutPlanB.setSets(Arrays.asList(4, 4, 3, 3, 3));
        workoutPlanB.setReps(Arrays.asList(8, 8, 10, 10, 10));
        workoutPlanB.setUser(user);
        workoutPlanB = workoutPlanRepository.save(workoutPlanB);

        for (int i = 0; i < 5; i++) {
            Workout workoutA = new Workout();
            workoutA.setUser(user);
            workoutA.setWorkoutPlan(workoutPlanA);
            workoutA.setDateTime(LocalDateTime.now().minusDays(7 * i));
            workoutA = workoutRepository.save(workoutA);

            List<Set> setsA = new ArrayList<>();
            for(Exercise exercise : workoutA.getWorkoutPlan().getExercises()) {
                int repsBase = 0;
                double weightBase = 0;
                int sets = 0;

                switch (exercise.getName()) {
                    case "bench press": {
                        repsBase = 3;
                        weightBase = 75;
                        sets = 3;
                        break;
                    }
                    case "larsen press": {
                        repsBase = 6;
                        weightBase = 60;
                        sets = 4;
                        break;
                    }
                    case "barbell row": {
                        repsBase = 8;
                        weightBase = 50;
                        sets = 4;
                        break;
                    }
                    case "db shoulder press": {
                        repsBase = 8;
                        weightBase = 18;
                        sets = 3;
                        break;
                    }
                    case "lat pullover": {
                        repsBase = 10;
                        weightBase = 15;
                        sets = 3;
                        break;
                    }
                    case "powell raise": {
                        repsBase = 12;
                        weightBase = 5;
                        sets = 2;
                        break;
                    }
                }

                for (int k = 0; k < sets; k++) {
                    Set set = new Set();
                    set.setWorkout(workoutA);
                    set.setExercise(exercise);
                    set.setReps(repsBase + random.nextInt(5) - 2);
                    set.setWeight(weightBase + random.nextInt(11) - 5);
                    set.setDateTime(workoutA.getDateTime());
                    set = setRepository.save(set);
                    setsA.add(set);
                }
            }

            workoutA.setSets(setsA);
            workoutRepository.save(workoutA);

            Workout workoutB = new Workout();
            workoutB.setUser(user);
            workoutB.setWorkoutPlan(workoutPlanB);
            workoutB.setDateTime(LocalDateTime.now().minusDays(7 * i));
            workoutB = workoutRepository.save(workoutB);

            List<Set> setsB = new ArrayList<>();
            for(Exercise exercise : workoutB.getWorkoutPlan().getExercises()) {
                int repsBase = 0;
                double weightBase = 0;
                int sets = 0;

                switch (exercise.getName()) {
                    case "incline bench press": {
                        repsBase = 8;
                        weightBase = 55;
                        sets = 4;
                        break;
                    }
                    case "cable row": {
                        repsBase = 8;
                        weightBase = 35;
                        sets = 4;
                        break;
                    }
                    case "lateral raise": {
                        repsBase = 10;
                        weightBase = 8;
                        sets = 3;
                        break;
                    }
                    case "triceps extensions": {
                        repsBase = 10;
                        weightBase = 30;
                        sets = 3;
                        break;
                    }
                    case "biceps curls": {
                        repsBase = 10;
                        weightBase = 25;
                        sets = 3;
                        break;
                    }
                }

                for (int k = 0; k < sets; k++) {
                    Set set = new Set();
                    set.setWorkout(workoutB);
                    set.setExercise(exercise);
                    set.setReps(repsBase + random.nextInt(5) - 2);
                    set.setWeight(weightBase + random.nextInt(11) - 5);
                    set.setDateTime(workoutB.getDateTime());
                    set = setRepository.save(set);
                    setsB.add(set);
                }
            }

            workoutB.setSets(setsB);
            workoutRepository.save(workoutB);
        }
    }
}
