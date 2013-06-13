package supersql.sql.templates;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.apache.log4j.Logger;

import supersql.ast.actions.ScriptAction;

/**
 * Created with IntelliJ IDEA. User: ian Date: 17/01/13 Time: 23:50 To change
 * this template use File | Settings | File Templates.
 */
public class ActionTemplateManager
{

  private final static Logger log = Logger
      .getLogger(ActionTemplateManager.class);
  
  private boolean check;

  private Map<String, ActionTemplate> actionTemplates;

  private Map<String, ActionTemplateFactory> actionTemplateFactories;

  public ActionTemplateManager() {
    this(true);
  }
  
  public ActionTemplateManager(boolean check) {
    this.check = check;
    this.actionTemplates = new HashMap<String, ActionTemplate>();
    this.actionTemplateFactories = new HashMap<String, ActionTemplateFactory>();
  }

  public ActionTemplate getActionTemplate(String prefix, ScriptAction action) {
    String actionCode = action.getCode();
    return getActionTemplate(prefix, actionCode);
  }

  public ActionTemplate getActionTemplate(String prefix, String actionCode)
  {
    ActionTemplate actionTemplate = actionTemplates.get(actionCode);
    if (actionTemplate == null) {
      String resource = actionCode + ".sql";
      InputStream stream = getClass().getResourceAsStream(resource);
      String pathToResources = "src/main/resources/templates/";
      String pathname = pathToResources + prefix + "/" + actionCode + (!check?"-nocheck":"") + ".sql";
      File file = new File(pathname);
      if (!check && !file.exists())
      {
        log.warn("Could not find file " + pathname);
        file = new File(pathToResources + prefix + "/" + actionCode + ".sql");
      }
      log.debug("Trying to scan " + resource);
      StringBuffer sb = new StringBuffer();
      try {
        Scanner scanner = new Scanner(file);
        while (scanner.hasNext()) {
          String s = scanner.nextLine();
          sb.append(s + "\n");
        }
        sb.append("\n");
        ActionTemplateFactory f = actionTemplateFactories.get(actionCode);
        if (f != null) {
          actionTemplate = f.create(sb.toString());
        }
        else {
          actionTemplate = new ActionTemplate(sb.toString());
        }

        actionTemplates.put(actionCode, actionTemplate);
      }
      catch (Exception e) {
        log.warn("Resource not found :" + prefix + "/" + resource);
        // return empty action template
        ActionTemplate emptyTemplate = new ActionTemplate("-- Action :"
            + actionCode + "(no template found)\n\n");
        actionTemplates.put(actionCode, emptyTemplate);
        return emptyTemplate;
      }

    }
    return actionTemplate;
  }

  public void setActionTemplate(String code, ActionTemplate template) {
    actionTemplates.put(code, template);
  }

  public void setActionTemplateFactory(String code,
                                       ActionTemplateFactory templateFactory)
  {
    actionTemplateFactories.put(code, templateFactory);
  }
}
