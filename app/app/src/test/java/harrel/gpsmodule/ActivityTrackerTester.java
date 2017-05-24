package harrel.gpsmodule;

import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import android.content.SharedPreferences;


/**
 * Created by szkatu on 24.05.2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class ActivityTrackerTester {

    @Mock
    Kierowca k;


@Test
    public void drivingtimetest()
{
k.czasProwadzenia=270*60;
    assertThat(k.jazda_przerwa(),is(true));



}
    @Test
    public void pausetimeTest()
    {
        k.czasProwadzenia=60;
        assertThat(k.jazda_przerwa(),is(false));



    }
    @Test
    public void drivingtimewithpausetest1()
    {
        k.czasProwadzenia=270*60;
        k.skroconoPrzerwe=true;
        assertThat(k.jazda_skroconaPrzerwa(),is(false));



    }
    @Test
    public void drivingtimewithpausetest2()
    {
        k.czasProwadzenia=60;
        k.skroconoPrzerwe=true;
        assertThat(k.jazda_skroconaPrzerwa(),is(false));



    }
    @Test
    public void drivingtimewithpausetest3()
    {
        k.czasProwadzenia=270*60;
        k.skroconoPrzerwe=false;
        assertThat(k.jazda_skroconaPrzerwa(),is(false));



    }
    @Test
    public void drivingtimewithpausetest4()
    {
        k.czasProwadzenia=60;
        k.skroconoPrzerwe=false;
        assertThat(k.jazda_skroconaPrzerwa(),is(true));



    }
    @Test
    public void drivingtimeweektest1()
    {
        k.czasProwadzenia=60;
        k.czasPracyTyg=56*60*60;
        k.czasOstatniegoOdpoczynku=6*24*60*60;
        k.czasPracy2Tyg=90*60*60;
        k.skroconoPrzerwe=false;
        assertThat(k.jazda_odpoczynekTygodniowy(),is(true));



    }
    @Test
    public void drivingtimeweektest2()
    {
        k.czasProwadzenia=60;
        k.czasPracyTyg=56*60;
        k.czasOstatniegoOdpoczynku=6*24*60*60;
        k.czasPracy2Tyg=90*60;
        k.skroconoPrzerwe=false;
        assertThat(k.jazda_odpoczynekTygodniowy(),is(true));



    }
    @Test
    public void drivingtimeweektest6()
    {
        k.czasProwadzenia=60;
        k.czasPracyTyg=56*60;
        k.czasOstatniegoOdpoczynku=6*24*60;
        k.czasPracy2Tyg=90*60;
        k.skroconoPrzerwe=false;
        assertThat(k.jazda_odpoczynekTygodniowy(),is(false));



    }
    @Test
    public void drivingtimeweektest3()
    {
        k.czasProwadzenia=60;
        k.skroconoPrzerwe=false;
        assertThat(k.jazda_skroconaPrzerwa(),is(true));



    }
    @Test
    public void drivingtimeweektest4()
    {
        k.czasProwadzenia=60;
        k.czasOdpoczynku=600000000;
        k.skroconoPrzerwe=false;
        assertThat(k.odpoczynekTygodniowy_jazda(),is(true));



    }
    @Test
    public void drivingtimeweektest5()
    {
        k.czasProwadzenia=60;
        k.czasOdpoczynku=60;
        k.skroconoPrzerwe=false;
        assertThat(k.odpoczynekTygodniowy_jazda(),is(false));



    }




}
