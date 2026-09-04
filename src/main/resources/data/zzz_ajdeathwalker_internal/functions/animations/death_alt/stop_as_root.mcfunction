scoreboard players set @s aj.ajdeathwalker.animation.death_alt.local_anim_time 0
tag @s remove aj.ajdeathwalker.animation.death_alt
execute on passengers run data modify entity @s interpolation_duration set value 0
tag @s add aj.ajdeathwalker.disable_command_keyframes
function zzz_ajdeathwalker_internal:animations/death_alt/tree/leaf_0
tag @s remove aj.ajdeathwalker.disable_command_keyframes