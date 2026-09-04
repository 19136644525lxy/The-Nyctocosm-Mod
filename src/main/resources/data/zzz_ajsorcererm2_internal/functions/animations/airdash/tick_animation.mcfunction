scoreboard players add @s aj.ajsorcererm2.animation.airdash.local_anim_time 1
scoreboard players operation @s aj.anim_time = @s aj.ajsorcererm2.animation.airdash.local_anim_time
function zzz_ajsorcererm2_internal:animations/airdash/apply_frame_as_root
execute if score @s aj.ajsorcererm2.animation.airdash.local_anim_time matches 65.. run function zzz_ajsorcererm2_internal:animations/airdash/end