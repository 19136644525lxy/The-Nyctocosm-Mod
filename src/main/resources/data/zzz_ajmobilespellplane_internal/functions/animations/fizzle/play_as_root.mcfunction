scoreboard players set @s aj.anim_time 0
scoreboard players set @s aj.ajmobilespellplane.animation.fizzle.local_anim_time 0
scoreboard players set @s aj.ajmobilespellplane.animation.fizzle.loop_mode 2
execute on passengers run data modify entity @s interpolation_duration set value 0
function zzz_ajmobilespellplane_internal:animations/fizzle/tree/leaf_0
execute on passengers run data modify entity @s interpolation_duration set value 1
tag @s add aj.ajmobilespellplane.animation.fizzle