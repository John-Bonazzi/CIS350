package prototype;

class ExpireTask extends TimerTask
{
  YourClass callbackClass;

  ExpireTask(YourClass callbackClass)
  {
    this.callbackClass = callbackClass;
  }

  public void run()
  {
    callbackClass.timeExpired()
  }
}


Timer timer = new Timer();
timer.schedule(new ExpireTask(callbackClass), 60000 /* 60 secs */);
