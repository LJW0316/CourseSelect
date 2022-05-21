<template>
  <div id="report" style="width: 1000px;height: 400px;"></div>
</template>

<script>
import echarts from 'echarts'
export default {
  name:"graph",
  components:{},
  data() {
    return {
      charts: "",
      score: "100",
      source: [
        ["题型", "总分", "成绩"],
        ["单选题", 45, 12],
        ["多选题", 30, 15],
        ["判断题", 45, 8],
        ["填空题", 10, 2]
      ]
    };
  },
}
</script>