scoreboard players set @s aj.anim_time 0
scoreboard players set @s aj.ajemissary.animation.phase2.attack.sword_plunge.local_anim_time 0
scoreboard players set @s aj.ajemissary.animation.phase2.attack.sword_plunge.loop_mode 2
execute on passengers run data modify entity @s interpolation_duration set value 0
function animated_java:ajemissary/zzzzzzzz/animations/phase2.attack.sword_plunge/tree/leaf_0
execute on passengers run data modify entity @s interpolation_duration set value 1
tag @s add aj.ajemissary.animation.phase2.attack.sword_plunge