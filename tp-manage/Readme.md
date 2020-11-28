**线程池管理后台**

**功能点**

- 创建线程池配置
   - 新增至统一配置中心/入库到MySQL
   - 包含参数
      1. 应用名称
      2. 线程池名称
      3. 核心线程数（动态修改）
      4. 最大线程数 (动态修改)
      5. 队列类型 (新增即确定、包含SynchronousQueue(同步队列)/
      LinkedBlockingQueue(无界阻塞队列)/ResizeableCapacityLinkedBlockingQueue(有界阻塞队列))
      6. 队列长度 （SynchronousQueue、LinkedBlockingQueue类型无法修改、ResizeableCapacityLinkedBlockingQueue可修改）
      7. 是否告警  （是、否）
      8. 队列容量告警阈值 （SynchronousQueue不可设置）
      9. 活跃度告警值 （活跃线程数量）
- 修改线程池配置
   - MySQL对应线程池配置修改
   - 调用对应服务的线程池接口进行动态修改参数
- 删除线程池配置
   - MySQL对应线程池配置删除
- 查看线程池状态
   - 根据prometheus收集到的数据，进行线程池监控展示

