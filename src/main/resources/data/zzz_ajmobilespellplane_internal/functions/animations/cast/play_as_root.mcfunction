scoreboard players set @s aj.anim_time 0
scoreboard players set @s aj.ajmobilespellplane.animation.cast.local_anim_time 0
scoreboard players set @s aj.ajmobilespellplane.animation.cast.loop_mode 2
execute on passengers run data modify entity @s interpolation_duration set value 0
function zzz_ajmobilespellplane_internal:animations/cast/tree/leaf_0
execute on passengers run data modify entity @s interpolation_duration set value 1
tag @s add aj.ajmobilespellplane.animation.cast