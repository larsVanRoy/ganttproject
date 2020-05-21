package net.sourceforge.ganttproject.chart;

import biz.ganttproject.core.calendar.GanttDaysOff;
import biz.ganttproject.core.chart.canvas.Canvas;
import biz.ganttproject.core.chart.canvas.Painter;
import biz.ganttproject.core.time.CalendarFactory;
import biz.ganttproject.core.time.GanttCalendar;
import net.sourceforge.ganttproject.action.resource.AssignmentToggleAction;
import net.sourceforge.ganttproject.action.resource.ResourceNewAction;
import net.sourceforge.ganttproject.action.task.TaskNewAction;
import net.sourceforge.ganttproject.resource.HumanResource;
import net.sourceforge.ganttproject.resource.HumanResourceManager;
import net.sourceforge.ganttproject.task.Task;
import net.sourceforge.ganttproject.task.TaskManager;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class RenderTests extends RenderTestCase {
    public void testTaskAndDayOff(){
        ResourceLoadRenderer renderer = makeResourceLoadRenderer();

        ResourceNewAction resourceNewAction = makeNewResourceAction();
        TaskNewAction taskNewAction = makeNewTaskAction();

        HumanResourceManager resourceManager = getHumanResourceManger();
        TaskManager taskManager = getTaskManager();

        resourceNewAction.actionPerformed(null);
        taskNewAction.actionPerformed(null);

        Task task_0 = taskManager.getTask(0);
        task_0.setStart(CalendarFactory.createGanttCalendar(2020, Calendar.MAY, 20));
        task_0.setEnd(CalendarFactory.createGanttCalendar(2020, Calendar.MAY, 21));

        HumanResource resource = resourceManager.getById(0);
        resource.addDaysOff(new GanttDaysOff(new Date(120, Calendar.MAY, 21), new Date(120, Calendar.MAY, 25)));

        AssignmentToggleAction assignmentToggleAction_0 = makeAssignmentToggleAction(resource, task_0);
        assignmentToggleAction_0.putValue(Action.SELECTED_KEY, true);
        assignmentToggleAction_0.actionPerformed(null);

        renderer.render();
        Canvas temp = renderer.getCanvas();
        temp.paint(makePainter());

        assertEquals(2, getShapes().size());
        assertEquals("dayoff", getShapes().get(0).getStyle());
        assertEquals(44, getShapes().get(0).getTopY());
        assertEquals(54, getShapes().get(0).getBottomY());
        assertEquals(20, getShapes().get(0).getLeftX());
        assertEquals(64, getShapes().get(0).getRightX());
        assertEquals("load.normal.first.last", getShapes().get(1).getStyle());
        assertEquals(44, getShapes().get(1).getTopY());
        assertEquals(54, getShapes().get(1).getBottomY());
        assertEquals(0, getShapes().get(1).getLeftX());
        assertEquals(20, getShapes().get(1).getRightX());
    }


}