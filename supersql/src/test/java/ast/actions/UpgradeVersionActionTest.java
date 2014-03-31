package ast.actions;

import junit.framework.Assert;

import org.junit.Test;

import supersql.ast.actions.UpgradeVersionAction;

public class UpgradeVersionActionTest
{

  @Test
  public void test() {
    UpgradeVersionAction upgradeVersionAction = new UpgradeVersionAction("1.2.3", "1.2.4");
    Assert.assertEquals("1.2.%", upgradeVersionAction.getPrevious());
  }

}
