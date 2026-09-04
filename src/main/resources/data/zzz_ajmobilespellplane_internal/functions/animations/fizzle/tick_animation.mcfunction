scoreboard players add @s aj.ajmobilespellplane.animation.fizzle.local_anim_time 1
scoreboard players operation @s aj.anim_time = @s aj.ajmobilespellplane.animation.fizzle.local_anim_time
function zzz_ajmobilespellplane_internal:animations/fizzle/apply_frame_as_root
execute if score @s aj.ajmobilespellplane.animation.fizzle.local_anim_time matches 14.. run function zzz_ajmobilespellplane_internal:animations/fizzle/end