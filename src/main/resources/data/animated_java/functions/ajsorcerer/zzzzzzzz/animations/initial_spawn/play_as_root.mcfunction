scoreboard players set @s aj.anim_time 0
scoreboard players set @s aj.ajsorcerer.animation.initial_spawn.local_anim_time 0
scoreboard players set @s aj.ajsorcerer.animation.initial_spawn.loop_mode 2
execute on passengers run data modify entity @s interpolation_duration set value 0
function animated_java:ajsorcerer/zzzzzzzz/animations/initial_spawn/tree/leaf_0
execute on passengers run data modify entity @s interpolation_duration set value 1
tag @s add aj.ajsorcerer.animation.initial_spawn