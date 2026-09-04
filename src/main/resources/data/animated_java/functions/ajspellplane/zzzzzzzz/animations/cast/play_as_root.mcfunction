scoreboard players set @s aj.anim_time 0
scoreboard players set @s aj.ajspellplane.animation.cast.local_anim_time 0
scoreboard players set @s aj.ajspellplane.animation.cast.loop_mode 2
execute on passengers run data modify entity @s interpolation_duration set value 0
function animated_java:ajspellplane/zzzzzzzz/animations/cast/tree/leaf_0
execute on passengers run data modify entity @s interpolation_duration set value 1
tag @s add aj.ajspellplane.animation.cast