package com.design.patterns.filter_patterns;

import java.util.List;

/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.filter_patterns
 * @date 2016/4/1 9:28
 */
public class AndCriterial implements Criterial {
    private Criterial maleCriterial;
    private Criterial femaleCriterial;
    public AndCriterial(Criterial maleCriterial,Criterial femaleCriterial) {
        this.maleCriterial = maleCriterial;
        this.femaleCriterial = femaleCriterial;
    }
    @Override
    public  List<Person>  filterCriterial(List<Person> personList) {
        List<Person> maleList = maleCriterial.filterCriterial(personList);
        return femaleCriterial.filterCriterial(maleList);
    }
}
