package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {

        // TODO #1 - get the Job with the given ID and pass it into the view
        Job job = jobData.findById(id);
        model.addAttribute(job);

        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.


        if (!errors.hasErrors()){

            Job job = new Job();

            job.setName(jobForm.getName());

            for (Employer employer : jobForm.getEmployers()){

                if (employer.getId() == jobForm.getEmployerId()){
                    job.setEmployer(employer);
                    break;
                }
            }

            for (Location location : jobForm.getLocations()){

                if (location.getId() == jobForm.getLocationId()){
                    job.setLocation(location);
                    break;
                }
            }

            for (CoreCompetency coreCompetency : jobForm.getCoreCompetencies()){

                if (coreCompetency.getId() == jobForm.getCoreCompetenciesId()){
                    job.setCoreCompetency(coreCompetency);
                    break;
                }
            }

            for (PositionType positionType : jobForm.getPositionTypes()){

                if (positionType.getId() == jobForm.getPositionTypesId()){
                    job.setPositionType(positionType);
                    break;
                }
            }

            jobData.add(job);
            int id = job.getId();
            return "redirect:?id=" + id;
        }


        return "job/new-job";

    }
}
