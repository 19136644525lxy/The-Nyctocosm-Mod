scoreboard players set @s aj.anim_time 0
scoreboard players set @s aj.ajemissary.animation.phase3_transition.local_anim_time 0
scoreboard players set @s aj.ajemissary.animation.phase3_transition.loop_mode 2
execute on passengers run data modify entity @s interpolation_duration set value 0
function animated_java:ajemissary/zzzzzzzz/animations/phase3_transition/tree/leaf_0
execute on passengers run data modify entity @s interpolation_duration set value 1
tag @s add aj.ajemissary.animation.phase3_transition