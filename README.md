# BACKCHANNEL

<p align="center">
  <strong>BACKCHANNEL · P2P CONNECTED</strong><br/>
  <em>Private by nature. Connected by choice.</em>
</p>

<p align="center">
  A privacy-first Android messaging project exploring direct device-to-device communication,
  with a clear path from nearby/offline connections to worldwide encrypted messaging.
</p>

---

## What is BACKCHANNEL?

BACKCHANNEL is being built around a simple idea: private conversations should not require handing over a phone number, email address, or social account just to connect.

The long-term product is an Android app where people create a cryptographic identity on their own device, exchange identities directly, verify who they are talking to, and communicate using encrypted message and media channels.

**The goal is not to build another public social network.** BACKCHANNEL is intended for deliberate, person-to-person and small-group communication.

## Product principles

- **Identity belongs to the device:** create an app identity locally; no phone number, email, OTP, or social sign-in as a prerequisite.
- **Private by default:** message content and attachments should be encrypted end-to-end.
- **Verify contacts:** QR-based identity exchange and a human-checkable safety fingerprint help users detect impersonation or key changes.
- **Nearby and worldwide:** support local connections when devices are close, and internet-based peer connections when they are far apart.
- **No plaintext message store:** any signaling or relay infrastructure should forward connection data or ciphertext, not readable conversation content.
- **Honest security:** use established cryptographic protocols and libraries, document metadata exposure, and do not claim security properties before they are implemented and tested.

## Planned capabilities

| Area | Intended capability |
|---|---|
| Identity | Device-generated identity, unique public identifier, QR sharing |
| Contact setup | Scan or share QR, add by BACKCHANNEL ID, verify safety fingerprint |
| Private chats | One-to-one text conversations |
| Group chats | Small private groups with explicit member/key management |
| Media | Image, voice-note, and file attachments |
| Nearby transport | Bluetooth and Wi-Fi Direct where supported by Android devices |
| Internet transport | WebRTC DataChannels with signaling and TURN fallback |
| Local protection | Encrypted local history, Android Keystore-backed key protection |
| Safety | Contact verification, key-change warnings, app lock, replay/tamper protections |
| Recovery | A user-controlled, securely designed identity backup and recovery flow |
| Experience | English interface, dark/light themes, calm midnight/indigo visual language |

## Connection model

BACKCHANNEL is designed for two connection situations:

1. **Nearby / offline:** devices may establish a local link using supported Android transports such as Bluetooth or Wi-Fi Direct. Android version, permissions, device manufacturer behavior, and background restrictions affect what is possible.
2. **Worldwide / internet:** peers can attempt a direct WebRTC connection. A signaling service is needed to help peers find and negotiate a connection; STUN helps with NAT traversal, and a TURN relay may be needed when a direct route cannot be established.

A relay can still observe some connection metadata such as IP addresses, timing, and traffic volume. End-to-end encryption protects message content when correctly implemented; it does not make all metadata disappear. TURN credentials and service secrets must never be committed to this repository.

## Security approach

Security-sensitive features must be based on reviewed, established designs—not custom cryptography invented for this app.

The implementation roadmap includes:

- Android Keystore for protecting locally held key material.
- Authenticated key agreement and forward-secure session design using established libraries/protocols.
- Authenticated encryption for messages and attachments.
- Safety fingerprints and clear warnings when a contact's identity key changes.
- Encrypted local persistence and careful handling of backups, logs, notifications, and temporary files.
- Replay protection, message framing validation, and safe attachment limits.

**Current security status:** this README describes the intended design, not a claim that all these protections are already implemented. The app must undergo code review, dependency review, multi-device testing, and security testing before being described as production-secure.

## Repository status

This repository is the starting point for the native Android application and its cloud build pipeline.

The first milestone is to establish a buildable Android app, preserve the agreed BACKCHANNEL visual direction, and make a debug APK available through GitHub Actions. Real P2P transports, audited end-to-end encryption, media delivery, and group key management are subsequent implementation milestones.

No demo-only web wrapper is intended to be the final Android product.

## Build an APK in the cloud

You can build from your phone; Android Studio is not required on your device.

1. Open this repository on GitHub.
2. Select **Actions**.
3. Choose **Build Android APK** from the workflow list.
4. Tap **Run workflow** and select the `main` branch.
5. Wait for the run to finish successfully.
6. Open the completed run and download the **BACKCHANNEL-debug-apk** artifact.
7. Extract the ZIP and install the APK on your Android phone. Android may ask you to allow installs from the browser or file manager.

The debug APK is for development and testing. For a stable shareable release, BACKCHANNEL will need a release signing key stored securely in GitHub Actions secrets. Never paste that key or its passwords into chat or commit them to the repository.

## Development roadmap

- [ ] Native Android project and repeatable cloud APK build
- [ ] Approved visual system and core navigation
- [ ] Local identity creation and secure key storage
- [ ] QR identity exchange and contact verification
- [ ] Encrypted local conversation storage
- [ ] One-to-one text messaging over an internet peer transport
- [ ] Nearby Bluetooth / Wi-Fi Direct transport
- [ ] Image, voice-note, and file transfer
- [ ] Private group conversations and group key lifecycle
- [ ] Identity backup and recovery
- [ ] Multi-device, network, privacy, and security testing
- [ ] Signed release APK

## Development and testing notes

- A real conversation test requires at least two Android devices.
- Nearby transport needs testing across different Android versions and manufacturers.
- Internet peer connectivity must be tested on different Wi-Fi and mobile networks, including restrictive NATs.
- A successful APK build proves that the project compiles; it does not prove that messaging or cryptography is complete.

## Contributing

Changes should keep the privacy-first product principles intact. Avoid adding analytics, ad SDKs, phone-number identity, contact-book uploads, or plaintext message logging without an explicit product and privacy review.

## License

No open-source license has been selected yet. Until a license is added, all rights are reserved by the repository owner.
