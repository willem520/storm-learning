package com.gome.bigData.demo.bolt;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author weiyu@gomeholdings.com
 * @description
 * @create 2017/6/28
 */
public class ReportBolt implements IRichBolt {
    private static final Logger log = LoggerFactory.getLogger(ReportBolt.class);
    private Map<String, Long> counts = null;

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        counts = new HashMap<>();
    }

    @Override
    public void execute(Tuple input) {
        String word = input.getStringByField("word");
        Long count = input.getLongByField("count");
        counts.put(word, count);
        printReport();
    }

    @Override
    public void cleanup() {

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        //无下游输出,不需要代码
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }

    //主要用于将结果打印出来,便于观察
    private void printReport() {
        log.info("--------------------------begin-------------------");
        Set<String> words = counts.keySet();
        for (String word : words) {
            log.info("@report-bolt@: " + word + " ---> " + counts.get(word));
        }
        log.info("--------------------------end---------------------");
    }
}
