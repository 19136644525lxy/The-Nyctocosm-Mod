scoreboard players set @s aj.animation.idle.local_anim_time 0
tag @s remove aj.death_walker.animation.idle
execute on passengers run data modify entity @s interpolation_duration set value 0
function zzz_death_walker_internal:animations/idle/tree/leaf_0