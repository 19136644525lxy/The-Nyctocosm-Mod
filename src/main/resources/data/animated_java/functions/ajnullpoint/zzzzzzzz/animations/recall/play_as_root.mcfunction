scoreboard players set @s aj.anim_time 0
scoreboard players set @s aj.ajnullpoint.animation.recall.local_anim_time 0
scoreboard players set @s aj.ajnullpoint.animation.recall.loop_mode 2
execute on passengers run data modify entity @s interpolation_duration set value 0
function animated_java:ajnullpoint/zzzzzzzz/animations/recall/tree/leaf_0
execute on passengers run data modify entity @s interpolation_duration set value 1
tag @s add aj.ajnullpoint.animation.recall