#built using mc-build (https://github.com/mc-build/mc-build)

effect clear @s blindness
effect clear @s slowness
effect clear @s jump_boost
execute at @s run playsound nyctocosm:auror.unblind master @s ~ ~ ~
tellraw @s [{"translate":"dialogue.nyctocosm.emissary_defeat.prompt_escape","color":"aqua","underlined":true,"clickEvent":{"action":"run_command","value":"/execute as @s[predicate=emissary:in_domain] run function emissary:escape"}}]
function emissary:cutscene/end