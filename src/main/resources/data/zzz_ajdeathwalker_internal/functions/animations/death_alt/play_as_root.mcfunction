scoreboard players set @s aj.anim_time 0
scoreboard players set @s aj.ajdeathwalker.animation.death_alt.local_anim_time 0
scoreboard players set @s aj.ajdeathwalker.animation.death_alt.loop_mode 2
execute on passengers run data modify entity @s interpolation_duration set value 0
function zzz_ajdeathwalker_internal:animations/death_alt/tree/leaf_0
execute on passengers run data modify entity @s interpolation_duration set value 1
tag @s add aj.ajdeathwalker.animation.death_alt