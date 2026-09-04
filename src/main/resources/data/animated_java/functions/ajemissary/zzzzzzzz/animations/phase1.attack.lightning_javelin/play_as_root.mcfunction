scoreboard players set @s aj.anim_time 0
scoreboard players set @s aj.ajemissary.animation.phase1.attack.lightning_javelin.local_anim_time 0
scoreboard players set @s aj.ajemissary.animation.phase1.attack.lightning_javelin.loop_mode 2
execute on passengers run data modify entity @s interpolation_duration set value 0
function animated_java:ajemissary/zzzzzzzz/animations/phase1.attack.lightning_javelin/tree/leaf_0
execute on passengers run data modify entity @s interpolation_duration set value 1
tag @s add aj.ajemissary.animation.phase1.attack.lightning_javelin