scoreboard players set @s aj.animation.walk.local_anim_time 0
scoreboard players set @s aj.loop_mode 0
scoreboard players operation @s aj.anim_time = @s aj.animation.walk.local_anim_time
execute on passengers run data modify entity @s interpolation_duration set value 0
function zzz_death_walker_internal:animations/walk/tree/leaf_0
execute on passengers store result entity @s interpolation_duration int 1 run scoreboard players get $aj.default_interpolation_duration aj.i
tag @s add aj.death_walker.animation.walk