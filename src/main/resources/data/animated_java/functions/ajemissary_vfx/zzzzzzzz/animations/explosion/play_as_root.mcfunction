scoreboard players set @s aj.anim_time 0
scoreboard players set @s aj.ajemissary_vfx.animation.explosion.local_anim_time 0
scoreboard players set @s aj.ajemissary_vfx.animation.explosion.loop_mode 1
execute on passengers run data modify entity @s interpolation_duration set value 0
function animated_java:ajemissary_vfx/zzzzzzzz/animations/explosion/tree/leaf_0
execute on passengers run data modify entity @s interpolation_duration set value 1
tag @s add aj.ajemissary_vfx.animation.explosion