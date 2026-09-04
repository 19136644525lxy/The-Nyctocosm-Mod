#built using mc-build (https://github.com/mc-build/mc-build)

tellraw @s[advancements={nyctocosm:bosses/defeat_emissary=true}] [{"translate":"dialogue.nyctocosm.emissary_defeat.prompt_escape","color":"aqua","underlined":true,"clickEvent":{"action":"run_command","value":"/execute as @s[predicate=emissary:in_domain] run function emissary:escape"}}]
advancement grant @s[advancements={nyctocosm:bosses/defeat_emissary=false}] only nyctocosm:bosses/defeat_emissary